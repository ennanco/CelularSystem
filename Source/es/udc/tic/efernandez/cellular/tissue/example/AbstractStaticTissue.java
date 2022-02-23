package es.udc.tic.efernandez.cellular.tissue.example;


import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.util.exception.InternalErrorException;
import es.udc.tic.efernandez.util.matrix.JMatrix;

public abstract class AbstractStaticTissue implements Tissue {

	protected JMatrix cells;
    protected Enviroment enviroment;
    private long instant = 0;

	public abstract void setParameters(int row, int column, DNA dna,Enviroment enviroment);
	
    public Cell get(int row, int column){
        return (Cell)cells.getElement(row,column);
    }
    
    public void set(int row, int column, Cell c){
        cells.setElement(row,column,c);
    }
    
    public void iteration(){
        
        Cell cell = null;
        Protein[] proteinList = null;

        instant++;
        
        enviroment.update(instant);
        
        for(int i =0; i<cells.getMaxDimensionRow(); i++){
            for(int j = 0; j<cells.getMaxDimensionColum(); j++){
                cell = (Cell)cells.getElement(i,j);
                if((cell != null) && cell.isNew())
                    cell.setOld();
            }
        }

        for(int i =0; i<cells.getMaxDimensionRow(); i++)
            for(int j = 0; j<cells.getMaxDimensionColum(); j++){
                cell = (Cell)cells.getElement(i,j);
                if ((cell != null) && !(cell.isNew())) 
                    try{
                        
                        if(enviroment != null){
                            proteinList = enviroment.get(cell.getPosition(),cell.getInstant());
                            if(proteinList != null){
                            	for(int index = 0; index < proteinList.length; index++)
                            		cell.recive(proteinList[index]);
                            }
                        }
                        cell.iteration();
                    }catch (InternalErrorException e){
                        System.out.println("Error en la celula("+j+","+i+")\n");
                        e.printStackTrace();
                    }
            }
        for(int i =0; i<cells.getMaxDimensionRow(); i++)
            for(int j = 0; j<cells.getMaxDimensionColum(); j++){
                cell = (Cell)cells.getElement(i,j);
                if((cell != null) && cell.died())
                    cells.setElement(i,j,null);
            }
                    
    }

    public boolean validPosition(int row, int column) {

        return (row >= 0) &&(row < cells.getMaxDimensionRow()) 
        		&& (column >= 0) && (column < cells.getMaxDimensionColum())&&(enviroment.valid(row,column));
    }
    
    public Enviroment getEnviroment(){
        return enviroment;
    }
    
    public String toString(){
        String toReturn = "";
        
        for(int i =0; i< cells.getMaxDimensionRow(); i++){
            
            for(int j = 0; j< cells.getMaxDimensionColum(); j++){
                if(cells.getElement(i,j) != null)
                        toReturn += "o";
                else
                    toReturn += "_";
            }
            toReturn += "\n";
        }

        for(int i =0; i<cells.getMaxDimensionRow(); i++){
            
            for(int j = 0; j<cells.getMaxDimensionColum(); j++){
                if(cells.getElement(i,j) != null)
                        toReturn += (Cell)cells.getElement(i,j);
            }
        }
        
        return toReturn;
    }
}
