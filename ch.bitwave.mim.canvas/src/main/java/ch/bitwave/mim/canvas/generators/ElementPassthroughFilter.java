package ch.bitwave.mim.canvas.generators;

import ch.bitwave.mim.m2.core.MimElement;

public class ElementPassthroughFilter extends ElementFilter {

	public ElementPassthroughFilter() {
	}

	@Override
	public boolean accepts(final MimElement element) {
		return true;
	}
}
