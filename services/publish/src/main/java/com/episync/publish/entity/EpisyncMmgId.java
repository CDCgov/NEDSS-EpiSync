package com.episync.publish.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class EpisyncMmgId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String messageProfileIdentifier;
    private String localSubjectId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((localSubjectId == null) ? 0 : localSubjectId.hashCode());
        result = prime * result + ((messageProfileIdentifier == null) ? 0 : messageProfileIdentifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EpisyncMmgId other = (EpisyncMmgId) obj;
        if (localSubjectId == null) {
            if (other.localSubjectId != null)
                return false;
        } else if (!localSubjectId.equals(other.localSubjectId))
            return false;

        if (messageProfileIdentifier == null) {
            if (other.messageProfileIdentifier != null)
                return false;
        } else if (!messageProfileIdentifier.equals(other.messageProfileIdentifier))
            return false;
        return true;
    }
}
