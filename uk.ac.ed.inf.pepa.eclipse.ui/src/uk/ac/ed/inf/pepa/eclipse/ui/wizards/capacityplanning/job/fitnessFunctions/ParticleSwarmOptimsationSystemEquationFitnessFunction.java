package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.fitnessFunctions;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.Tool;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.ODEConfig;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.PEPAConfig;

public class ParticleSwarmOptimsationSystemEquationFitnessFunction extends SystemEquationFitnessFunction {
	
	public ParticleSwarmOptimsationSystemEquationFitnessFunction(PEPAConfig configPEPA, 
			ODEConfig configODE,
			HashMap<String,Double> targets,
			HashMap<String,Double> targetWeights,
			HashMap<String,Double> populationRanges,
			HashMap<String,Double> fitnessMap,
			IProgressMonitor monitor,
			Recorder recorder){
		
		super(configPEPA,configODE,targets,targetWeights,populationRanges,fitnessMap,monitor,recorder);
		
	}
	
	@Override
	public FitnessFunction copySelf(){
		FitnessFunction fitnessFunction = (FitnessFunction) new ParticleSwarmOptimsationSystemEquationFitnessFunction(configPEPA, 
				configODE,
				Tool.copyHashMap(targets),
				Tool.copyHashMap(targetWeights),
				Tool.copyHashMap(populationRanges),
				Tool.copyHashMap(fitnessMap),
				monitor,
				recorder);
		
		return fitnessFunction;
	}
	
	protected void setPopulationFitness(HashMap<String,Double> maxPopulation){
		
		this.populationResultsMap = new HashMap<String,Double>();
		
		for(Entry<String, Double> entry : candidate.entrySet()){
			String component = entry.getKey();
			Double value = entry.getValue();
			if(value > maxPopulation.get(entry.getKey())){
				Double range = this.populationRanges.get(component);
				Double weight = ((Integer) this.candidate.size()).doubleValue();
				value *= 10;
				this.populationResultsMap.put(component, ((value/range)*100)/weight);
			} else {
				Double range = this.populationRanges.get(component);
				Double weight = ((Integer) this.candidate.size()).doubleValue();
				this.populationResultsMap.put(component, ((value/range)*100)/weight);
			}
		}
		
		this.populationFitness = 0.0;
		
		for(Entry<String, Double> entry : this.populationResultsMap.entrySet()){
			Double value = entry.getValue();
			this.populationFitness += value;
		}
		
	}
	
	public Double getFitness(HashMap<String,Double> candidate, HashMap<String,Double> maximumPopulation){
		this.candidate = candidate;
		this.setFitness(maximumPopulation);
		return fitness;
	}
	
	private void setFitness(HashMap<String,Double> maximumPopulation){
		setPerformanceFitness();
		setPopulationFitness(maximumPopulation);
		Double alpha = this.fitnessMap.get(Config.FITNESS_ALPHA_PERFORMANCE_S);
		Double beta = this.fitnessMap.get(Config.FITNESS_BETA_POPULATION_S);
		this.fitness = (alpha*this.performanceFitness) + (beta*this.populationFitness);
	}
}