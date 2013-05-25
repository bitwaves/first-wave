package ch.bitwave.mim.canvas.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.NamedElement;
import ch.bitwave.mim.m2.core.Relationship;

public class TreeNode {

	private Relationship masterRelationship;
	private MimElement element;
	private List<TreeNode> details;
	private TreeNode parent;

	public TreeNode(final MimElement element) {
		this.element = element;
		this.details = new ArrayList<TreeNode>();
	}

	public TreeNode(final Relationship masterRelationship, final MimElement element) {
		this(element);
		this.masterRelationship = masterRelationship;
	}

	public MimElement getElement() {
		return this.element;
	}

	public void setElement(final MimElement element) {
		this.element = element;
	}

	public Relationship getMasterRelationship() {
		return this.masterRelationship;
	}

	public void setMasterRelationship(final Relationship masterRelationship) {
		this.masterRelationship = masterRelationship;
	}

	public List<TreeNode> getDetails() {
		return this.details;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeNode [masterRelationship=").append(this.masterRelationship)
				.append(", element=").append(this.element).append(", details=")
				.append(this.details).append("]");
		return builder.toString();
	}

	public void addDetail(@Nonnull final TreeNode detail) {
		this.details.add(detail);
		detail.setParent(this);
	}

	protected void setParent(final TreeNode treeNode) {
		this.parent = treeNode;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	public void sortRecursively(final Comparator<TreeNode> comparator) {
		Collections.sort(this.details, comparator);
		for (TreeNode detail : this.details) {
			detail.sortRecursively(comparator);
		}
	}

	public String getLabel() {
		if (this.element instanceof NamedElement) {
			return ((NamedElement) this.element).getName();
		}
		return "";
	}
}
