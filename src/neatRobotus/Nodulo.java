package neatRobotus;

import java.util.ArrayList;


public class Nodulo implements java.io.Serializable{ //Unidade que vai armazenar os valores entre conexões(sinapses).


	
	private static final long serialVersionUID = 1L;
	protected ArrayList<Conexao> posterior = new ArrayList<Conexao>();
	public int id = 0;
	private boolean ativado = false;
	
	//GETS E SETS
	
	public void ativar( double soma){ //define o valor do nódulo
		if( soma < 0)
			soma = soma*0.01;
		if( !this.posterior.isEmpty() && !ativado){
			ativado = true;
			for( int i =0; i < this.posterior.size(); i++){
				this.posterior.get(i).ativar( ((Math.exp(2*soma)-1)/(Math.exp(2*soma)+1)));
			}
			ativado = false;
		}
	}
	
	public Conexao getSaida( int i){
		return this.posterior.get(i);
	}
	
	public void addSaida( Conexao nova){
		this.posterior.add(nova);
	}
	
	
	//#FERRAMENTAS#
	
	public int getId(){
		return this.id;
	}
}

//#INPUT#

class Input extends Nodulo{


	private static final long serialVersionUID = 1L;
	
	protected String nome = "#";
	
	public String getNome(){
		return this.nome;
	}
}


//#OUTPUTS#

class Output extends Input{


	public Output( double modular, double min, double max){
		if( modular == 1)
			this.mod = true;
		this.min = min;
		this.max = max;
	}

	private static final long serialVersionUID = 1L;
	protected double valor = 0.0;
	protected double max;
	protected double min;
	protected boolean mod =false;
	
	public void ativar( double valor){
		this.valor = valor;
	}
	
	public double getValor(){
		return valor;
	}
	
	public double calcularSaida(){
		double valor = this.valor;
		this.valor = 0.0; 
		return ((Math.exp(0.08*valor)-1)/(Math.exp(0.08*valor)+1))*max;
	}
}

class Bias extends Input{
 
	public Bias() {}
	
	private static final long serialVersionUID = 1L;
	
	public void mutarAleatorio(){
		int rand = ( int) (Math.random()*1000)%this.posterior.size();
		this.posterior.get(rand).mutarPeso();
	}
}