package de.unistuttgart.ims.segmentation.uima.evaluation.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class Util {
	public static final String BEGIN = "BEGIN";
	public static final String END = "END";

	public static <SEGMENT_TYPE extends Annotation> BiFunction<SEGMENT_TYPE, String, String> getFunction() {
		return new BiFunction<SEGMENT_TYPE, String, String>() {

			public String apply(SEGMENT_TYPE t, String u) {
				return t.getType().getShortName() + "-" + u;
			}
		};
	}

	public static <BASE_TYPE extends Annotation, SEGMENT_TYPE extends Annotation, BOUNDARY_TYPE> List<Set<BOUNDARY_TYPE>> getBoundarySetSequence(
			JCas jcas, Class<SEGMENT_TYPE> segmentType, Class<BASE_TYPE> subType,
			BiFunction<SEGMENT_TYPE, String, BOUNDARY_TYPE> btFunction) {

		MultiValuedMap<Integer, SEGMENT_TYPE> begins = new HashSetValuedHashMap<Integer, SEGMENT_TYPE>(),
				ends = new HashSetValuedHashMap<Integer, SEGMENT_TYPE>();
		for (SEGMENT_TYPE st : JCasUtil.select(jcas, segmentType)) {
			begins.put(st.getBegin(), st);
			ends.put(st.getEnd(), st);
		}

		List<BASE_TYPE> coll = new ArrayList<BASE_TYPE>(JCasUtil.select(jcas, subType));
		ListSet<BOUNDARY_TYPE> r = new ListSet<BOUNDARY_TYPE>();
		for (int i = 0; i < coll.size() - 1; i++) {
			r.add(i, new HashSet<BOUNDARY_TYPE>());
		}
		for (int i = 0; i < coll.size(); i++) {
			BASE_TYPE anno = coll.get(i);
			if (begins.containsKey(anno.getBegin()) && i > 0) {
				for (SEGMENT_TYPE s : begins.get(anno.getBegin()))
					r.addSetItem(i - 1, btFunction.apply(s, BEGIN));
			}
			if (ends.containsKey(anno.getEnd()) && i < coll.size() - 1) {
				for (SEGMENT_TYPE s : ends.get(anno.getEnd()))
					r.addSetItem(i, btFunction.apply(s, END));
			}

		}
		return r;

	}

	static class ListSet<BOUNDARY_TYPE> extends ArrayList<Set<BOUNDARY_TYPE>> {

		private static final long serialVersionUID = 1L;

		public void addSetItem(int i, BOUNDARY_TYPE listItem) {
			this.get(i).add(listItem);
		}
	}
}
