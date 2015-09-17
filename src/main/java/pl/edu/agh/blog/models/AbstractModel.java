package pl.edu.agh.blog.models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModel implements Serializable {

	private static final long serialVersionUID = 9126816573921962971L;
	
	public static final String sequenceGeneratorName = "default_seq_gen"; 

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceGeneratorName)
	protected int id;

	public AbstractModel() {}
	
	public AbstractModel(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModel other = (AbstractModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
		
}
