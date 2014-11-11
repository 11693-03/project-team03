package edu.cmu.lti.oaqa.pipeline;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import json.JsonCollectionReaderHelper;
import json.gson.Question;
import json.gson.TestSet;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import util.TypeUtil;
import edu.cmu.lti.oaqa.type.retrieval.ConceptSearchResult;

/**
 * A simple collection reader that reads documents/questions from a directory in
 * the filesystem.
 * 
 */

public class QuestionReader extends CollectionReader_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of an input
	 * file.
	 */
	public static final String PARAM_INPUTPATH = "InputFile";

	private List<Question> inputs;

	private String mInputPath;
	private int mCurrentIndex;

	public void initialize() throws ResourceInitializationException {
		mInputPath = ((String) getConfigParameterValue(PARAM_INPUTPATH)).trim();
		mCurrentIndex = 0;
		// extract the input questions from file
		System.out.println(mInputPath);
		inputs = TrainingSet.load(getClass().getResourceAsStream(mInputPath)).stream().collect(toList());
		// trim question texts
		inputs.stream()
				.filter(input -> input.getBody() != null)
				.forEach(
						input -> input.setBody(input.getBody().trim()
								.replaceAll("\\s+", " ")));
	}

	@Override
	public void getNext(CAS aCAS) throws IOException, CollectionException {	  
		JCas jcas;
		try{
			jcas = aCAS.getJCas();
		}catch(CASException e){
			throw new CollectionException(e);
		}
		
		//add all the information to CAS using helper
		Question next = inputs.get(mCurrentIndex++);
		JsonCollectionReaderHelper.addQuestionToIndex(next, "", jcas);
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return mCurrentIndex < inputs.size();
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(mCurrentIndex, inputs.size(), Progress.ENTITIES) };
	}

	@Override
	public void close() throws IOException {
	}

}
