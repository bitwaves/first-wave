package ch.bitwave.mim.integration.delphi;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;

import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.driver.DelphiSourceParserDriver;
import ch.bitwave.mim.integration.toolbox.FileSystem;
import ch.bitwave.mim.m2.core.Classifier;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.PackageImport;
import ch.bitwave.mim.m2.core.StubClass;
import ch.bitwave.mim.m2.core.StubPackage;
import ch.bitwave.mim.m2.profiles.DelphiProfile;

/**
 * Integrates a Delphi AST into the mim workspace by transforming it into m2
 * elements.
 */
public class DelphiIntegrator {

	private static final Logger LOGGER = Logger.getLogger(DelphiIntegrator.class.getSimpleName());
	private File rootFolder;

	public File getRootFolder() {
		return this.rootFolder;
	}

	public void setRootFolder(final File rootFolder) {
		this.rootFolder = rootFolder;
	}

	public DelphiIntegrator(final File rootFolder) {
		this.rootFolder = rootFolder;
	}

	/**
	 * Integrates the given unit below the given target namespace. The package
	 * hierarchy is derived from the fully qualified path name stored in the
	 * unit as expressed relative to the absolute root folder specified for the
	 * integrator.
	 * 
	 * @param targetPackage
	 * @param unit
	 */
	public void integrate(final DelphiProfile profile, final MimPackage targetPackage,
			final Unit unit) {
		MimPackage parent = ensurePackageHierarchy(targetPackage, unit.getSourceLocation());
		DelphiASTMapper visitor = new DelphiASTMapper(profile, parent);
		unit.accept(visitor);
	}

	public void integrateFolder(final DelphiProfile profile, final MimPackage targetPackage,
			final File folder, final boolean recursive, final String encoding) throws IOException {
		LOGGER.info(String.format("Integrating folder %s.", folder.getAbsolutePath()));
		Collection<File> found = FileUtils.listFiles(folder, new String[] { "pas" }, recursive);
		for (File file : found) {
			integrateFile(profile, targetPackage, file, encoding);
		}
	}

	public void integrateFile(final DelphiProfile profile, final MimPackage targetPackage,
			final File file, final String encoding) throws IOException {
		LOGGER.info(String.format("Integrating file %s.", file.getAbsolutePath()));
		Unit unit = new DelphiSourceParserDriver().readFile(file, encoding);
		unit.setSourceLocation(file);
		integrate(profile, targetPackage, unit);
	}

	/**
	 * Attempts to replace all Stub classes with the actual classes they
	 * represent.
	 */
	public void resolveStubs(final MimPackage rootPackage) {
		resolvePackageStubs(rootPackage, rootPackage);
		resolveClassStubs(rootPackage);
	}

	private void resolvePackageStubs(final MimPackage rootPackage, final MimPackage subject) {
		List<PackageImport> imports = subject.getPackageImports();
		Map<String, MimPackage> nonStubs = rootPackage.getAllOwnedNonStubPackages(true);
		for (PackageImport pimp : imports) {
			MimPackage target = pimp.getImportedPackage();
			if (target.isStub()) {
				StubPackage stub = (StubPackage) target;
				MimPackage resolved = nonStubs.get(stub.getName().toLowerCase());
				if (resolved != null) {
					stub.replaceWith(resolved);
					LOGGER.info(String.format("Replaced %s with %s.", stub, resolved));
				}
			}
		}
		for (MimPackage subPackage : subject.getNestedPackages()) {
			resolvePackageStubs(rootPackage, subPackage);
		}
	}

	private void resolveClassStubs(final MimPackage rootPackage) {
		List<StubClass> stubs = rootPackage.getOwnedMembersOfType(StubClass.class);
		Map<String, MimClass> nonStubs = rootPackage.getAllNonStubClasses(true);
		for (StubClass stub : stubs) {
			Classifier resolved = nonStubs.get(stub.getName().toLowerCase());
			if (resolved instanceof MimClass) {
				stub.replaceWith((MimClass) resolved);
			}
		}
		for (MimPackage subPackage : rootPackage.getNestedPackages()) {
			resolveClassStubs(subPackage);
		}
	}

	/**
	 * <p>
	 * Returns the effective package in which to place the types introduced in
	 * the given unit, based on the given source location of the unit in
	 * relation to the integrator's root folder. Existing packages are reused.
	 * </p>
	 * 
	 * Example: If the integrator root is "C:/source/" and the unit location is
	 * "C:/source/magic/mushroom/dust.pas" then the resulting package will be
	 * root.magic.mushroom.
	 * 
	 * @param rootPackage
	 *            the package below which to add detail packages.
	 * @param sourceLocation
	 *            the location of the unit, including the unit file name.
	 * @return
	 */
	protected MimPackage ensurePackageHierarchy(@Nonnull final MimPackage rootPackage,
			@Nonnull final File sourceLocation) {
		File sourceFolder = sourceLocation.getParentFile();
		String[] packageNames = FileSystem.getRelativeFolders(this.rootFolder, sourceFolder);
		if (packageNames.length == 0) {
			return rootPackage;
		}
		MimPackage result = rootPackage;
		for (String packageName : packageNames) {
			result = ensureDetailPackage(result, packageName);
		}
		return result;
	}

	private MimPackage ensureDetailPackage(final MimPackage parent, final String packageName) {
		MimPackage detailMember = parent.findOwnedMemberAs(packageName, MimPackage.class);
		if (detailMember == null) {
			detailMember = new MimPackage();
			detailMember.setName(packageName);
			parent.addOwnedMember(detailMember);
		}
		return detailMember;
	}
}
