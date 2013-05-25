package ch.bitwave.mim.integration.delphi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.driver.DelphiSourceParserDriver;
import ch.bitwave.mim.integration.testbase.Resources;
import ch.bitwave.mim.m2.core.Classifier;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.Operation;
import ch.bitwave.mim.m2.core.UnknownMemberException;
import ch.bitwave.mim.m2.profiles.DelphiProfile;

/**
 * Tests the integration of Delphi sources into the metamodel.
 */
public class DelphiIntegratorTest {

	@Test
	public void shouldMapPhoenixInterfaces() throws IOException, UnknownMemberException {
		DelphiIntegrator integrator = createIntegrator();
		DelphiProfile profile = new DelphiProfile();
		MimPackage rootPackage = new MimPackage();
		rootPackage.setName("root");
		integrateUnit(integrator, "product/common/", "SampleUnit7.pas", profile, rootPackage);
		MimPackage phoenixPackage = (MimPackage) rootPackage.getOwnedMemberByName("product");
		MimPackage sharedPackage = (MimPackage) phoenixPackage.getOwnedMemberByName("common");
		MimPackage pxiPackage = (MimPackage) sharedPackage.getOwnedMemberByName("SampleUnit7");
		assertEquals(5, pxiPackage.getOwnedMembersOfType(MimClass.class).size());
		MimClass clientLanguage = (MimClass) pxiPackage.getOwnedMemberByName("IClientLanguage");
		assertEquals("IClientLanguage", clientLanguage.getName());
		assertEquals(pxiPackage, clientLanguage.getNamespace());
		MimClass person = (MimClass) pxiPackage.getMemberByName("IPerson");
		assertEquals(1, person.getGenerals().size());
		Classifier general = person.getGenerals().get(0);
		assertEquals("IHuman", general.getName());
		assertTrue(general.isStereotype(profile.getInterfaceStereotype()));
	}

	@Test
	public void shouldResolveStubClasses() throws IOException, UnknownMemberException {
		DelphiIntegrator integrator = createIntegrator();
		DelphiProfile profile = new DelphiProfile();
		MimPackage rootPackage = new MimPackage();
		rootPackage.setName("root");
		integrateUnit(integrator, "product/common/", "SampleUnit7.pas", profile, rootPackage);
		integrateUnit(integrator, "independent/toolbox/", "SampleUnit3.pas", profile, rootPackage);
		integrator.resolveStubs(rootPackage);
		MimPackage tbi = rootPackage.findContainedPackage("SampleUnit3");
		MimClass stordef = (MimClass) tbi.getOwnedMemberByName("IStorableDefinition");
		MimPackage pxi = rootPackage.findContainedPackage("SampleUnit7");
		MimClass dawg = (MimClass) pxi.getOwnedMemberByName("IDogDefinition");
		assertTrue(dawg.isSpecializationOf(stordef));

	}

	@Test
	public void shouldMapImplementationIdentifierDependencies() throws IOException,
			UnknownMemberException {
		DelphiIntegrator integrator = createIntegrator();
		DelphiProfile profile = new DelphiProfile();
		MimPackage rootPackage = new MimPackage();
		rootPackage.setName("root");
		integrateUnit(integrator, "varia", "Planets.pas", profile, rootPackage);
		MimPackage planets = rootPackage.findContainedPackage("Varia").findContainedPackage(
				"Planets");
		MimClass mercury = (MimClass) planets.getOwnedMemberByName("TMercury");
		assertFalse(mercury.depdendsOnIdentifier("cstrGUID_Venus"));
		Operation method = (Operation) mercury.getOwnedMemberByName("DoThing");
		assertTrue(method.depdendsOnIdentifier("cstrGUID_Venus"));

	}

	private DelphiIntegrator createIntegrator() {
		// Let's fake a relative location of the unit in subfolders
		// phoenix/shared to force creating of the respective package tree.
		String rootPath = "D:/delphi-source/";
		File rootFolder = new File(rootPath);
		DelphiIntegrator integrator = new DelphiIntegrator(rootFolder);
		return integrator;
	}

	private void integrateUnit(final DelphiIntegrator integrator, final String relativePath,
			final String fileName, final DelphiProfile profile, final MimPackage rootPackage)
			throws IOException {
		File file = new File(Resources.RESOURCE_FOLDER + fileName);
		File unitLocation = new File(new File(integrator.getRootFolder(), relativePath), fileName);
		Unit unit = new DelphiSourceParserDriver().readFile(file, Resources.ENCODING);
		// System.out.print(DelphiASTPrinter.writeToString(unit));
		unit.setSourceLocation(unitLocation);
		integrator.integrate(profile, rootPackage, unit);
	}

}
