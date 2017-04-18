package de.unistuttgart.ims.segmentation.uima.evaluation.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import com.google.common.collect.Sets;

public class Util {
	public static <BASE_TYPE extends Annotation, SEGMENT_TYPE extends Annotation, BOUNDARY_TYPE> List<Set<BOUNDARY_TYPE>> getBoundarySetSequence(
			JCas jcas, Class<SEGMENT_TYPE> segmentType, Class<BASE_TYPE> subType,
			BiFunction<SEGMENT_TYPE, String, BOUNDARY_TYPE> btFunction) {

		Set<Integer> begins = Sets.newHashSet(), ends = Sets.newHashSet();
		for (SEGMENT_TYPE st : JCasUtil.select(jcas, segmentType)) {
			begins.add(st.getBegin());
			ends.add(st.getEnd());
		}

		Collection<BASE_TYPE> coll = JCasUtil.select(jcas, subType);
		ListSet<BOUNDARY_TYPE> r = new ListSet<BOUNDARY_TYPE>();
		for (int i = 0; i < coll.size() - 1; i++) {
			r.add(i, new HashSet<BOUNDARY_TYPE>());
		}
		int i = 0;
		for (final BASE_TYPE anno : coll) {

			if (begins.contains(anno.getBegin())) {
				r.addSetItem(i, btFunction.apply(null, "begin"));
			}
			if (ends.contains(anno.getEnd())) {
				r.addSetItem(i + 1, btFunction.apply(null, "end"));
			}

			i++;
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
