package org.opentripplanner.api.parameter;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

public class QualifiedMode implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public final String mode;
    public final Set<String> qualifiers = Sets.newHashSet();

    public QualifiedMode(String qMode) {
        String[] elements = qMode.split("_");
        mode = elements[0].trim();
        for (int i = 1; i < elements.length; i++) {
            String q = elements[i].trim();
            qualifiers.add(q);
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mode);
        for (String qualifier : qualifiers) {
            sb.append("_");
            sb.append(qualifier);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return mode.hashCode() * qualifiers.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof QualifiedMode) {
            QualifiedMode qmOther = (QualifiedMode) other;
            return qmOther.mode.equals(this.mode) && qmOther.qualifiers.equals(this.qualifiers);
        }
        return false;
    }

}