package org.springframework.roo.classpath.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.roo.classpath.PhysicalTypeCategory;
import org.springframework.roo.model.CustomData;
import org.springframework.roo.model.DataType;
import org.springframework.roo.model.JavaPackage;
import org.springframework.roo.model.JavaType;

/**
 * Unit test of {@link DefaultItdTypeDetails}
 * 
 * @author Andrew Swan
 * @since 1.2.0
 */
public class DefaultItdTypeDetailsTest extends ItdTypeDetailsTestCase {

    private static final String MINIMAL_ITD = "// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.\n"
            + "// You may push code into the target .java compilation unit if you wish to edit any member(s).\n"
            + "\n"
            + "package com.foo.bar;\n"
            + "\n"
            + "import com.foo.bar.Person;\n"
            + "\n"
            + "aspect Person_Roo_Extra {\n" + "    \n" + "}\n";

    @Test
    public void testMinimalInstance() {
        // Set up
        final boolean privilegedAspect = false;
        final int modifier = 42;

        final ClassOrInterfaceTypeDetails mockGovernor = mock(ClassOrInterfaceTypeDetails.class);
        when(mockGovernor.getPhysicalTypeCategory()).thenReturn(
                PhysicalTypeCategory.CLASS);

        final JavaPackage mockPackage = mock(JavaPackage.class);
        when(mockPackage.getFullyQualifiedPackageName()).thenReturn(
                "com.foo.bar");

        final JavaType mockGovernorType = mock(JavaType.class);
        when(mockGovernorType.getPackage()).thenReturn(mockPackage);
        when(mockGovernor.getType()).thenReturn(mockGovernorType);
        when(mockGovernorType.getSimpleTypeName()).thenReturn("Person");
        when(mockGovernorType.getFullyQualifiedTypeName()).thenReturn(
                "com.foo.bar.Person");
        when(mockGovernorType.getDataType()).thenReturn(DataType.TYPE);

        final CustomData mockCustomData = mock(CustomData.class);

        final JavaType mockAspectType = mock(JavaType.class);
        when(mockAspectType.getPackage()).thenReturn(mockPackage);
        when(mockAspectType.isDefaultPackage()).thenReturn(false);
        when(mockAspectType.getSimpleTypeName()).thenReturn("Person_Roo_Extra");

        final JavaType mockImportType = mock(JavaType.class);
        when(mockImportType.getSimpleTypeName()).thenReturn("Person");
        when(mockImportType.getFullyQualifiedTypeName()).thenReturn(
                "com.foo.bar.Person");
        when(mockImportType.getDataType()).thenReturn(DataType.TYPE);

        final String declaredByMetadataId = "MID:foo#bar";

        // Invoke
        final DefaultItdTypeDetails itd = new DefaultItdTypeDetails(
                mockCustomData, declaredByMetadataId, modifier, mockGovernor,
                mockAspectType, privilegedAspect,
                Arrays.asList(mockImportType), null, null, null, null, null,
                null, null, null, null);

        // Check
        assertEquals(0, itd.getAnnotations().size());
        assertEquals(0, itd.getDeclaredConstructors().size());
        assertEquals(0, itd.getDeclaredFields().size());
        assertEquals(0, itd.getDeclaredInitializers().size());
        assertEquals(0, itd.getDeclaredInnerTypes().size());
        assertEquals(0, itd.getDeclaredMethods().size());
        assertEquals(0, itd.getExtendsTypes().size());
        assertEquals(0, itd.getFieldAnnotations().size());
        assertEquals(0, itd.getImplementsTypes().size());
        assertEquals(0, itd.getInnerTypes().size());
        assertEquals(0, itd.getMethodAnnotations().size());
        assertEquals(1, itd.getRegisteredImports().size());

        assertEquals(mockAspectType, itd.getAspect());
        assertEquals(mockCustomData, itd.getCustomData());
        assertEquals(declaredByMetadataId, itd.getDeclaredByMetadataId());
        assertEquals(modifier, itd.getModifier());
        assertEquals(mockGovernorType, itd.getType());
        assertEquals(DefaultItdTypeDetails.PHYSICAL_TYPE_CATEGORY,
                itd.getPhysicalTypeCategory());
        assertEquals(privilegedAspect, itd.isPrivilegedAspect());
        assertEquals(mockGovernor, itd.getGovernor());
        assertFalse(itd.extendsType(mock(JavaType.class)));

        assertOutput(MINIMAL_ITD, itd);
    }
}