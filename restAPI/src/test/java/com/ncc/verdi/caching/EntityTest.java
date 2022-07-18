package com.ncc.verdi.caching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class EntityTest {
    @Test
    public void testGetResolvedType() {
        assertEquals("PER", Entity.getResolvedType(Set.of(
            "PER.MilitaryPersonnel",
            "PER.MilitaryPersonnel.MilitaryOfficer",
            "PER.Politician",
            "PER.Politician.HeadOfGovernment",
            "PER.ProfessionalPosition.Journalist",
            "PER.ProfessionalPosition.Minister",
            "PER.ProfessionalPosition.Scientist")));
        assertEquals("PER.ProfessionalPosition", Entity.getResolvedType(Set.of(
            "PER.ProfessionalPosition.Journalist",
            "PER.ProfessionalPosition.Minister",
            "PER.ProfessionalPosition.Scientist")));
        assertEquals("PER.Politician", Entity.getResolvedType(Set.of(
            "PER.Politician",
            "PER.Politician.HeadOfGovernment")));
        assertEquals("VEH", Entity.getResolvedType(Set.of(
            "VEH",
            "VEH.Tank",
            "PER.ProfessionalPosition.Minister",
            "PER.ProfessionalPosition.Scientist")));
    }

    @Test
    public void testGetHumanReadableType() {
        assertEquals("Person", Entity.getHumanReadableType("PER"));
        assertEquals("Professional Position", Entity.getHumanReadableType("PER.ProfessionalPosition"));
        assertEquals("Politician", Entity.getHumanReadableType("PER.Politician"));
        assertEquals("Vehicle", Entity.getHumanReadableType("VEH"));
    }
}
