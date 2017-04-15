package de.unistuttgart.ims.segmentation.uima.evaluation.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import de.unistuttgart.ims.commons.Counter;
import de.unistuttgart.ims.segmentation.type.SegmentationUnit;
import de.unistuttgart.ims.segmentation.uima.evaluation.util.ContinuousSegmentBoundaryAnnotator;

@Deprecated
public class SegmentationUtil {

	public static int[] getMassTuple(JCas jcas, Class<? extends Annotation> boundaryType) {
		final Collection<? extends Annotation> boundaries = JCasUtil.select(jcas, boundaryType);
		final Counter<Annotation> segUnits = new Counter<Annotation>();
		for (final Annotation su : JCasUtil.select(jcas, SegmentationUnit.class)) {
			segUnits.add(su);
		}

		final int units = segUnits.size();
		// int[] masses = new int[boundaries.size() + 1];
		final List<Integer> massList = new LinkedList<Integer>();
		int i = 0;
		final int end = jcas.getDocumentText().length();
		Annotation prevAnno = null;
		Collection<? extends Annotation> coll;

		for (final Annotation anno : boundaries) {
			if (prevAnno == null) {
				// Case before the first segment
				coll = JCasUtil.selectPreceding(SegmentationUnit.class, anno, Integer.MAX_VALUE);
			} else {
				// cases between the first and last segment boundary
				coll = JCasUtil.selectBetween(SegmentationUnit.class, prevAnno, anno);
			}

			// System.err.println(JCasUtil.toText(coll));
			if (!coll.isEmpty())
				massList.add(coll.size());
			// masses[i++] = coll.size();
			segUnits.subtractAll(coll);
			prevAnno = anno;
		}
		// after the last segment boundary
		coll = null;
		if (prevAnno == null) {
			coll = JCasUtil.select(jcas, SegmentationUnit.class);
		} else
			coll = JCasUtil.selectBetween(SegmentationUnit.class, prevAnno, new Annotation(jcas, end + 1, end + 1));
		if (coll != null) {
			// System.err.println(i + ": " + coll.toString());
			// masses[i] = coll.size();
			if (!coll.isEmpty())
				massList.add(coll.size());

			// System.err.println(JCasUtil.toText(coll));
			segUnits.subtractAll(coll);

		}
		int s = 0;
		for (int j = 0; j < massList.size(); j++) {
			s += massList.get(j);
		}
		if (s != units) {
			System.err.println("units: " + units + ". Mass string: " + s);
		}
		if (segUnits.getHighestCount() > 0) {
			System.err.println(segUnits.getKeysWithMaxCount());
			System.err.println(JCasUtil.toText(segUnits.getKeysWithMaxCount()));
		}

		final int[] masses = new int[massList.size()];
		for (i = 0; i < massList.size(); i++) {
			masses[i] = massList.get(i);
		}

		return masses;
	}

	public static boolean[] getBoundaryString(int[] massString) {
		final boolean[] boundaries = new boolean[sum(massString)];
		Arrays.fill(boundaries, false);
		int index = 0;
		for (int i = 0; i < (massString.length - 1); i++) {
			index += massString[i];
			if (index < boundaries.length)
				boundaries[index - 1] = true;
		}
		return boundaries;
	}

	public static int sum(int[] array) {
		int s = 0;
		for (final int element : array) {
			s += element;
		}
		return s;
	}

	public static JCas segment2boundary(JCas jcas, Class<? extends Annotation> segmentClass)
			throws AnalysisEngineProcessException, ResourceInitializationException {
		SimplePipeline.runPipeline(jcas, AnalysisEngineFactory.createEngineDescription(ContinuousSegmentBoundaryAnnotator.class,
				ContinuousSegmentBoundaryAnnotator.PARAM_ANNOTATION_TYPE, segmentClass.getCanonicalName()));
		return jcas;

	}

}
