package de.unistuttgart.ims.segmentation.uima.evaluation.util;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import de.unistuttgart.ims.uimautil.TypeParameterUtil;

public class ContinuousSegmentBoundaryAnnotator extends JCasAnnotator_ImplBase {

	public static final String PARAM_SEGMENT_TYPE = "Annotation Type";
	public static final String PARAM_BOUNDARY_TYPE = "Boundary Type";

	@ConfigurationParameter(name = PARAM_SEGMENT_TYPE)
	String segmentTypeName = "";

	@ConfigurationParameter(name = PARAM_BOUNDARY_TYPE)
	String boundaryTypeName = "";

	Class<? extends Annotation> segmentType;
	Class<? extends Annotation> boundaryType;

	@Override
	public void initialize(final UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		segmentType = TypeParameterUtil.getClass(segmentTypeName);
		boundaryType = TypeParameterUtil.getClass(boundaryTypeName);
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		for (final Annotation anno : JCasUtil.select(jcas, segmentType)) {
			final int b = anno.getBegin();
			AnnotationFactory.createAnnotation(jcas, b, b, boundaryType);
		}
	}

}
