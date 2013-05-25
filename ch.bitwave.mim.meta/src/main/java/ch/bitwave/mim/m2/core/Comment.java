package ch.bitwave.mim.m2.core;

public class Comment extends MimElement {
	private String body;

	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public MimElement getAnnotatedElement() {
		return getOwner();
	}

	public void setAnnotatedElement(final MimElement annotatedElement) {
		this.setOwner(annotatedElement);
	}

}
