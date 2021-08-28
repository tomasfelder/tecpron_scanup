package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the LeicaScanningProject type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LeicaScanningProjects")
public final class LeicaScanningProject implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField NAME = field("name");
  public static final QueryField START_DATE = field("startDate");
  public static final QueryField SCANNER = field("leicaScanningProjectScannerId");
  public static final QueryField TECPRON_PROJECT = field("leicaScanningProjectTecpronProjectId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String startDate;
  private final @ModelField(targetType="Scanner") @BelongsTo(targetName = "leicaScanningProjectScannerId", type = Scanner.class) Scanner scanner;
  private final @ModelField(targetType="TecpronProject") @BelongsTo(targetName = "leicaScanningProjectTecpronProjectId", type = TecpronProject.class) TecpronProject tecpronProject;
  private final @ModelField(targetType="Station") @HasMany(associatedWith = "leicaScanningProject", type = Station.class) List<Station> stations = null;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getStartDate() {
      return startDate;
  }
  
  public Scanner getScanner() {
      return scanner;
  }
  
  public TecpronProject getTecpronProject() {
      return tecpronProject;
  }
  
  public List<Station> getStations() {
      return stations;
  }
  
  private LeicaScanningProject(String id, String name, String startDate, Scanner scanner, TecpronProject tecpronProject) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.scanner = scanner;
    this.tecpronProject = tecpronProject;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LeicaScanningProject leicaScanningProject = (LeicaScanningProject) obj;
      return ObjectsCompat.equals(getId(), leicaScanningProject.getId()) &&
              ObjectsCompat.equals(getName(), leicaScanningProject.getName()) &&
              ObjectsCompat.equals(getStartDate(), leicaScanningProject.getStartDate()) &&
              ObjectsCompat.equals(getScanner(), leicaScanningProject.getScanner()) &&
              ObjectsCompat.equals(getTecpronProject(), leicaScanningProject.getTecpronProject());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getStartDate())
      .append(getScanner())
      .append(getTecpronProject())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LeicaScanningProject {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("startDate=" + String.valueOf(getStartDate()) + ", ")
      .append("scanner=" + String.valueOf(getScanner()) + ", ")
      .append("tecpronProject=" + String.valueOf(getTecpronProject()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static LeicaScanningProject justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new LeicaScanningProject(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      startDate,
      scanner,
      tecpronProject);
  }
  public interface NameStep {
    StartDateStep name(String name);
  }
  

  public interface StartDateStep {
    BuildStep startDate(String startDate);
  }
  

  public interface BuildStep {
    LeicaScanningProject build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep scanner(Scanner scanner);
    BuildStep tecpronProject(TecpronProject tecpronProject);
  }
  

  public static class Builder implements NameStep, StartDateStep, BuildStep {
    private String id;
    private String name;
    private String startDate;
    private Scanner scanner;
    private TecpronProject tecpronProject;
    @Override
     public LeicaScanningProject build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LeicaScanningProject(
          id,
          name,
          startDate,
          scanner,
          tecpronProject);
    }
    
    @Override
     public StartDateStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep startDate(String startDate) {
        Objects.requireNonNull(startDate);
        this.startDate = startDate;
        return this;
    }
    
    @Override
     public BuildStep scanner(Scanner scanner) {
        this.scanner = scanner;
        return this;
    }
    
    @Override
     public BuildStep tecpronProject(TecpronProject tecpronProject) {
        this.tecpronProject = tecpronProject;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String startDate, Scanner scanner, TecpronProject tecpronProject) {
      super.id(id);
      super.name(name)
        .startDate(startDate)
        .scanner(scanner)
        .tecpronProject(tecpronProject);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder startDate(String startDate) {
      return (CopyOfBuilder) super.startDate(startDate);
    }
    
    @Override
     public CopyOfBuilder scanner(Scanner scanner) {
      return (CopyOfBuilder) super.scanner(scanner);
    }
    
    @Override
     public CopyOfBuilder tecpronProject(TecpronProject tecpronProject) {
      return (CopyOfBuilder) super.tecpronProject(tecpronProject);
    }
  }
  
}
