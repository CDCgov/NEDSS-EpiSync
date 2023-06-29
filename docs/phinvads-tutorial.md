# PHINVADS Cheat Sheet

At the primitive type level, health data is very simple.  Everything is a number, a string, a date, an ID, a valueset, and a smattering of other types.

This document is my attempt to understand how CDC's PHINVADS stores Value Sets (aka enumerations), which are the crucial common language of all of health care.

I pull this info mostly from this pdf --- scroll way down to page 88(page 94 in the pdf) and you'll find an explanation of the PHINVADS data model:   https://phinvads.cdc.gov/vads/downloads/PHINVADS_Guide.pdf

This is Jim's cheat sheet summary of what I found there

There are six kinds of objects in PHINVADS.

1. Code Systems
2. Code System Concepts
3. Value Sets
4. Value Set Concepts
5. Groups.
6. Views (Message Guides)

Here are quick descriptions of each, followed by some further notes at the bottom:

## Code System

A `Code System` is a collection of Code System Concepts.  Many Code Systems are from other organizations.  Example Code Systems are: HL7, ICD-9, ICD-9 CM, SNOMED CT, LOINC, and CPT.

??? Is a Code System versioned?  It seems not?

A Code System has a unique OID.  Example OID:  2.16.840.1.113883.6.96 (This happens to be the OID for SNOMED)

#### Fancy HL7 Version 3 Core Principles Definition:

A code system is a managed collection of concept identifiers, usually codes, but sometimes more complex sets of rules and references. They are often described as collections of uniquely identifiable concepts with associated representations, designations, associations, and meanings. To meet the requirements of a code system as defined by HL7, a given concept representation must resolve to one and only one meaning within the code system. In the terminology model, a code system is represented by the Code System class. Code systems are often referred to as terminologies, vocabularies, or coding schemes.

## Code System Concept

A `Code System Concept` is a Code System's code and description for a single concept - an atomic unit of thought.

Example:  In ICD-9 487.0 is Influenza

Code System Concepts are used to develop Value Sets.

??? Its not clear if Code SYstem Concepts are versioned.  Maybe not. If you need to change a Code System Concept, maybe you just create a new one and stop using the old one.

#### Fancy HL7 Version 3 Core Principles Definition for Concept:

A _concept_ defines a unitary mental representation of a real or abstract thing; an atomic unit of thought. It should be unique in a given code system. A concept may have synonyms in terms of representation and it may be a primitive or compositional term.

I think this might be a _compositional_ example: "Leg" is a body part, but at the same time so is "Foot", "Toe, "Knee", "Femur", etc.  (See Ezekiel 37)



## ValueSet

A `Value Set` is a versioned collection of Value Set Concepts, grouped together for a purpose.  The Value Set concepts can be pulled from multiple Code Systems - that is, a Value Set does not necessarily belong to one Code System!

Example Value Set:  Body Site -- a collection of body part Value Set Concepts

A ValueSet has a unique OID.  Example OID: 2.16.840.1.114222.4.11.908

#### Fancy HL7 Version 3 Core Principles Definition:

A Value Set represents a uniquely identifiable set of valid concept representations, where any concept representation can be tested to determine whether or not it is a member of the value set.

## Value Set Concept

A Value Set Concept is the code and name for a concept _as used in a Value Set_ (e.g. Arm, Leg).

??? Its not clear if a Value Set Concept is somehow a subtly different object from a Code System Concept, or not.   

Presumably one Value Set Concept can be in many Value Sets.

??? It looks like Value Set Concepts have concept codes (eg MSG114, INV165). (I think Code System Concepts also have conceptCodes) Note: These same concept codes show up as the `QuestionIdentifier` in the XML.

??? I think Value Set Concepts (like Code Set Concepts) are not versioned.


## Group

A Group is a collection of like Value Sets categorized by a subject or a theme.   Example Groups:
- _Demographics_: contains Value Sets like Country, County, City, etc.
- _Laboratory_:  contains Value Sets like: Specimen Type, Hepatitis Lab Test, Microorganism.

One ValueSet can belong to many Groups.
One Group contains many Value Sets.

??? Per the diagram on p92(pdf page 98), it appears that Groups are NOT versioned?  That is, the Group is a set of ValueSet, not a set of ValueSetVersion!



## View

A View is a _versioned_ collection of versioned Value Sets used by an implementation guide, like Tuberculosis or HAI (Healthcare Associated Infection).

One ValueSet can belong to many Views.
One View contains many Value Sets.

Note:  In the various ER diagrams a `View` is called a `VocabularyView`.

??? Why isn't a View a collection of Groups?  It seems like the Grouping getts lost when you put the ValueSets into the View directly.   Like, a mapping guide would want to pull in all the _Demographics_ questions.


# Other Notes on PHINVADS

### Software Development

??? It does not appear that PHINVADS is being actively worked on. Most recent releases were to fix the log4j security issue.  Prior release appears to mainly be library dependency updates.

See the [PHINVADS Release Notes pdf](https://phinvads.cdc.gov/vads/downloads/4_0_6_6_Release_Notes.pdf)


### Combining Code Systems

The Code Systems from `Standards Determining Organizations`.

Each Code System (eg, LOINC, SNOMED) has its own way of describing its code objects.   PHINVADS has to glom these all together, which takes some thought and work.   Here's their description of how that's done:

[Code System Representation pdf](https://phinvads.cdc.gov/vads/DownloadCodeSystemRepresentation.action)

??? How often does PHINVAD update from the Standards Determining Organizations?
??? how does PHINVADs find out when a code system has been updated?


### Question Bank

PHINVADS also plays the role of a Question Bank, inside of a Value Set called PHIN Questions.

There is a Code System and a Value Set both (mildly confusingly) with the same name, `PHIN Questions`.

The individual Value Set Concepts inside `PHIN Questions` are the questions.

This is super confusing, because you kinda expect the Value Set to be a Question, then the Value Set Concepts to be possible answers to the question.  But that's not how its organized.

For example, The Value Set Concept with concept code `INV169` is `Condition Code`, that is, the name of your disease or health condition. [Link to Condition Code value set concept](https://phinvads.cdc.gov/vads/ViewValueSetConcept.action?id=BBB085CA-4611-418D-9122-36B3A9C76983)  Of course, there are thousands of conditions/diseases, so you would expect `Condition Code` to be a Value Set - but its not!  Its a Value Set Concept.

So where is the actual list of diseases and conditions stored? If you [drill down into the Condition Code concept on the website](https://phinvads.cdc.gov/vads/ViewCodeSystemConcept.action?oid=2.16.840.1.114222.4.5.232&code=INV169), you'll see the list associated in an  _Other Relationships_ link.

To summarize, the Questions themselves show up as low level objects ("concepts") in PHINVADS inside various Question Value Sets.  And the answers to those questions show up as the value set concepts in a different Value Sets, and they appear to be linked using the `Other Relationships`.

Interestingly this is a Value Set called  `Data Elements (NND Gen V2)` (which ironically is on version 1) that has 66 Value Set Concepts in it - and those Value Set Concepts are in this case _Questions_, not answers to questions!!


