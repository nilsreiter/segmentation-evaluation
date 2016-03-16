package de.unistuttgart.ims.segmentation.evaluation.util;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnnotationFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import de.unistuttgart.ims.segmentation.type.SegmentationUnit;

public class SegmentationUnitAnnotator extends JCasAnnotator_ImplBase {

	public static final String PARAM_ANNOTATION_TYPE = "Annotation Type";

	@ConfigurationParameter(name = PARAM_ANNOTATION_TYPE)
	String annotationTypeName = "";
	Class<? extends Annotation> annotationType;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(final UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		try {
			annotationType = (Class<? extends Annotation>) Class.forName(annotationTypeName);
		} catch (final ClassNotFoundException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		for (final Annotation anno : JCasUtil.select(jcas, annotationType)) {
			AnnotationFactory.createAnnotation(jcas, anno.getBegin(), anno.getEnd(), SegmentationUnit.class);
		}
	}

}
