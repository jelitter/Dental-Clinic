package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProcedureList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Procedure> list;
	
	public ProcedureList(ArrayList<Procedure> list) {
		this.set(list);
	}

	public void set(ArrayList<Procedure> list) { this.list = list;	}
	public ArrayList<Procedure> get() { return this.list;	}

	public void addProcedure(Procedure procedure) { list.add(procedure);	}
	public void removeProcedure(Procedure procedure) { list.remove(procedure); }
}
