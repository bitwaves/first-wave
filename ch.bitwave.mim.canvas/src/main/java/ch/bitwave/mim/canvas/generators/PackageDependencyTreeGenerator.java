package ch.bitwave.mim.canvas.generators;

import java.util.ArrayList;
import java.util.List;

import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.Namespace;
import ch.bitwave.mim.m2.core.PackageImport;

public class PackageDependencyTreeGenerator extends TreeGenerator {

	private boolean hideStubs;

	public PackageDependencyTreeGenerator(final boolean hideStubs) {
		super();
		this.hideStubs = hideStubs;
	}

	@Override
	public List<TreeNode> build(final MimPackage root) {
		List<MimPackage> elementSet = root.getAllOwnedPackages();
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (MimPackage mimPackage : elementSet) {
			result.add(buildNode(mimPackage));
		}
		return result;
	}

	private TreeNode buildNode(final Namespace pack) {
		List<PackageImport> importers = pack.getImportingPackages();
		TreeNode master = new TreeNode(pack);
		for (PackageImport imp : importers) {
			Namespace importing = imp.getImportingNamespace();
			if (!(this.hideStubs && importing.isStub())) {
				TreeNode detail = buildNode(importing);
				master.addDetail(detail);
			}
		}
		return master;
	}

}
