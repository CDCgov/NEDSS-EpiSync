# The PHINVADS Data Model

PHIN VADS stands for Public Health Information Network Vocabulary Access and Distribution System.

PHIN VADS is CDC's system for managing Value Sets - sets of enumerated names for things, such as the set of body parts, or the set of diseases, or the set of countries.  PHIN VADS manages about 2000 or so value sets.  The PHIN VADS website is here:  https://phinvads.cdc.gov/vads/SearchVocab.action. 

At the primitive type level, health data is very simple.  Everything is a number, a string, a date, an ID, or a Value Set (aka an enumeration)! (ok, ok, and probably a smattering of other types)

Value Sets are the crucial common primitive language across of all of health care, and as such they are incredibly important.  This document is my attempt to understand how CDC's PHIN VADS models Value Sets.  

(I pulled this info mostly from this pdf)[https://phinvads.cdc.gov/vads/downloads/PHINVADS_Guide.pdf] --- scroll way down to page 88(page 94 in the pdf) and you'll find an explanation of the PHIN VADS data model:   

This is Jim's summary of what I found there, for use by the EpiSync team as we work on protyping Question Templates.   _Please note that this is done as a learning exercise, and might have mistakes in it!!_

## 6 kinds of objects in PHIN VADS

There are six kinds of objects in PHIN VADS.

1. Code Systems
2. Code System Concepts
3. Value Sets
4. Value Set Concepts
5. Groups.
6. Views (Message Guides)

Here are quick descriptions of each, followed by some further notes at the bottom.  Versioning is a really big deal with these vocabularies.  So for each kind of object, I note whether it is versioned or not.

## 1. Code System

A `Code System` is a collection of Code System Concepts.  Many Code Systems are from other organizations called Standards Determing Organizations (SDO).  Sometimes we play a bit loose, and colloquially mix the SDO name with the Code System name: eg, HL7, ICD-9 CM, SNOMED CT, LOINC, and CPT.

Oversimplifying, SNOMED has things like clinical findings, symptoms, body parts, organisms.  LOINC has laboratory tests.  ICD-10 has 92,000 diagnoses, 82,000 procedures, and 11,000 diseases.  And so on.  As you can imagine, there is some redundancy and overlap.  (I think the PHIN VADS numbers only reflect the concepts that are actually used as Value Set Concepts)

Code Systems in PHIN VADS are versioned; it looks like PHIN VADS simply uses the SDO's version.   Currently, PHIN VADS lists about 200 Code Systems.

A Code System has a unique OID.  Example OID:  2.16.840.1.113883.6.96 (This happens to be the OID for SNOMED)

#### Fancy HL7 Version 3 Core Principles Definition:

A code system is a managed collection of concept identifiers, usually codes, but sometimes more complex sets of rules and references. They are often described as collections of uniquely identifiable concepts with associated representations, designations, associations, and meanings.  To meet the requirements of a code system as defined by HL7, a given concept representation must resolve to one and only one meaning within the code system. In the terminology model, a code system is represented by the Code System class. Code systems are often referred to as terminologies, vocabularies, or coding schemes.



## 2. Code System Concept

A `Code System Concept` is a Code System's code and description for a _single concept_ - an atomic unit of thought.

Example:  In ICD-9 487.0 is `Influenza`.

Just to give a sense of size, currently 508,000 Code System Concepts have been brought into PHIN VADS from SnoMed.

Code System Concepts can be grabbed and used as Value Set Concepts, in one or more Value Sets.

Code System Concepts are NOT versioned.  They are immutable.  If you need to change a Code System Concept, you just create a new one and stop using the old one.

#### Fancy HL7 Version 3 Core Principles Definition for Concept:

A _concept_ defines a unitary mental representation of a real or abstract thing; an atomic unit of thought. It should be unique in a given Code System. A concept may have synonyms in terms of representation and it may be a primitive or compositional term.

I think this might be a _compositional_ example: "Leg" is a body part, but at the same time so is "Foot", "Toe, "Knee", "Femur", etc.  (See Ezekiel 37)



## ValueSet

A `Value Set` is a versioned collection of Value Set Concepts, grouped together for a purpose.  The Value Set concepts can be pulled from multiple Code Systems - that is, a Value Set does not "belong" to a Code System!

Example Value Set:  Body Site -- a collection of body part Value Set Concepts

A ValueSet has a unique OID.  Example OID: 2.16.840.1.114222.4.11.908

#### Fancy HL7 Version 3 Core Principles Definition:

A Value Set represents a uniquely identifiable set of valid concept representations, where any concept representation can be tested to determine whether or not it is a member of the value set.



## Value Set Concept

A Value Set Concept is the code and name for a Code System Concept ***as used in a Value Set*** (e.g. Arm, Leg).

The only difference between a Value Set Concept and a Code System Concept is that Value Set Concepts are allowed to have a new Preferred Name (aka "Synonym",  or "Display name")

One Value Set Concept can be in many Value Sets.   So, obviously therefore one Code System Concept can be used in many Value Sets.

Both Value Set Concepts and Code System Concepts have *concept codes* (eg `MSG114`, `INV165`).   NBS Note: These *concept codes* are useful: they show up as the `QuestionIdentifier` in the NBS Template XML.  I _think_ they act like a primary key for the concept.

Like Code System Concept, Value Set Concepts are not versioned.  They are immutable.



## Group

A Group is a collection of like Value Sets categorized by a subject or a theme.   Example Groups:
- _Demographics_: contains Value Sets like Country, County, City, etc.
- _Laboratory_:  contains Value Sets like: Specimen Type, Hepatitis Lab Test, Microorganism.

One ValueSet can belong to many Groups.
One Group contains many Value Sets.

Per the diagram on p92(pdf page 98), it appears that Groups are NOT versioned.  (That is, the Group is a set of ValueSet, not a set of ValueSetVersion)

The Group feature is not widely utilized at this time.


## View

A View is a _versioned_ collection of versioned Value Sets.

Views are useful ways of keeping track of a set of Value Sets.  For example, a useful View would be the collection of Value Sets used by a particular implementation guide (MMG), like Tuberculosis or HAI (Healthcare Associated Infection).

One ValueSet can belong to many Views.
One View contains many Value Sets.

Note:  In the various ER diagrams a `View` is called a `VocabularyView`.


# Other Notes on PHIN VADS

### Software Development and Code System updates

Software updates are note here: [PHIN VADS Release Notes pdf](https://phinvads.cdc.gov/vads/downloads/4_0_6_6_Release_Notes.pdf)

PHIN VADS tends to pull in updates from other Code Systems a few times a year: [PHIN VADS Update Schedule pdf](https://phinvads.cdc.gov/vads/downloads/4_0_6_6_Release_Notes.pdf)

In a Covid-like crisis situation, PHIN VADS can do such Code System pulls/updates apart from the schedule.    Also, new or updated Value Sets can be created and added to PHIN VADS very quickly - a few hours turnaround time, in some cases.


### Combining Code Systems

The Code Systems come from `Standards Determining Organizations`.

Each Code System (eg, LOINC, SNOMED) has its own way of describing its code objects.  So PHIN VADS has the tricky job of glomming these all together into a coherent Value Set idea, which takes some thought and work.   Here's the PHIN VADS description of how that's done:

[Code System Representation pdf](https://phinvads.cdc.gov/vads/DownloadCodeSystemRepresentation.action)

### Question Bank

Its interesting to note that PHIN VADS also plays the role of a Question Bank, inside of a Value Set called PHIN Questions, and similar value sets.

There is a Code System and a Value Set both (mildly confusingly) with the same name, `PHIN Questions`.  The individual Value Set Concepts inside `PHIN Questions` are the questions.

This is a bit counter-intuitive, because you kinda expect the Value Set to be a Question, then the Value Set Concepts to be possible answers to the question.  But that's not how its organized.

For example, there is a Value Set Concept called `Condition Code`, that is, the name of your disease or health condition. ([See Condition Code value set concept here](https://phinvads.cdc.gov/vads/ViewValueSetConcept.action?id=BBB085CA-4611-418D-9122-36B3A9C76983))  Of course, there are thousands of conditions/diseases, so you would expect `Condition Code` to be a Value Set - but its not!  Its a Value Set Concept.

So where is the actual list of diseases and conditions stored? If you [drill down into the Condition Code concept on the website](https://phinvads.cdc.gov/vads/ViewCodeSystemConcept.action?oid=2.16.840.1.114222.4.5.232&code=INV169), you'll see the list associated in an  _Other Relationships_ link.

To summarize, the questions themselves show up as low level objects ("concepts") in PHIN VADS inside various Question Value Sets.  And the answers to those questions show up as the value set concepts in a different Value Sets, and they appear to be linked using the `Other Relationships`.

Example: **Gen V2**'s questions are stored this way.  There is a Value Set called  `Data Elements (NND Gen V2)` (which ironically is on version 1) that has 66 Value Set Concepts in it - and those Value Set Concepts are the set of generic _questions_ that make up the Generic V2 MMG.


