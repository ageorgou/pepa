package uk.ac.ed.inf.pepa.eclipse.ui.wizards.metaHeuristicCapacityPlanning.model;

import java.util.ArrayList;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;
import uk.ac.ed.inf.pepa.largescale.IParametricDerivationGraph;
import uk.ac.ed.inf.pepa.largescale.IPointEstimator;
import uk.ac.ed.inf.pepa.largescale.ISequentialComponent;
import uk.ac.ed.inf.pepa.largescale.simulation.IStatisticsCollector;
import uk.ac.ed.inf.pepa.parsing.ModelNode;

public class ODEAndPEPAModelConfig extends ModelType{
	
	private IPointEstimator[] estimators;
	private IStatisticsCollector[] collectors;
	private String[] labels;
	
	//The underlying PEPAModel
	private IPepaModel model;
	
	//An AST ModelNode
	private ModelNode node;
	
	//A derivation of the model for the wizard
	private IParametricDerivationGraph dGraph;

	public ODEAndPEPAModelConfig(String defaultType) {
		super(defaultType, null, "ODE Configuration");
	}

	@Override
	public void setOptions(String option) {
		super.options = null;
		
	}
	
	public void setEstimators(IPointEstimator[] estimators){
		this.estimators = estimators;
	}
	
	public IPointEstimator[] getEstimators(){
		return this.estimators;
	}
	
	public void setCollectors(IStatisticsCollector[] collectors){
		this.collectors = collectors;
	}
	
	public IStatisticsCollector[] getCollectors(){
		return this.collectors;
	}
	
	public void setLabels(String[] labels){
		this.labels = labels;
	}
	
	public String[] getLabels(){
		return this.labels;
	}
	
	public String[] getSystemEquation() {
		
		ArrayList<String> components = new ArrayList<String>();
		
		for (ISequentialComponent c : dGraph.getSequentialComponents()){
			components.add(c.getName());
		}
		
		return components.toArray(new String[0]);
	}
	
	public Integer[] getInitialPopulation() {
		
		ArrayList<Integer> components = new ArrayList<Integer>();
		
		for (ISequentialComponent c : dGraph.getSequentialComponents()){
			components.add(c.getInitialPopulationLevel());
		}
		
		return components.toArray(new Integer[0]);
	}

	public void setModels(IPepaModel model, ModelNode node,
			IParametricDerivationGraph dGraph) {
		this.model = model;
		this.node = node;
		this.dGraph = dGraph;
		
	}
	
	public IPepaModel getPepaModel(){
		return this.model;
	}
	
	public ModelNode getModelNode(){
		return this.node;
	}
}