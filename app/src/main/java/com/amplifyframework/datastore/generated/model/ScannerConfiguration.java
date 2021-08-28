package com.amplifyframework.datastore.generated.model;


import java.io.Serializable;
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

/** This is an auto generated class representing the ScannerConfiguration type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ScannerConfigurations")
public final class ScannerConfiguration implements Model, Serializable {
  public static final QueryField ID = field("id");
  public static final QueryField LOCATION = field("location");
  public static final QueryField RESOLUTION = field("resolution");
  public static final QueryField CAMERA = field("camera");
  public static final QueryField DOUBLE_SCAN = field("doubleScan");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String location;
  private final @ModelField(targetType="String", isRequired = true) String resolution;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean camera;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean doubleScan;
  public String getId() {
      return id;
  }
  
  public String getLocation() {
      return location;
  }
  
  public String getResolution() {
      return resolution;
  }
  
  public Boolean getCamera() {
      return camera;
  }
  
  public Boolean getDoubleScan() {
      return doubleScan;
  }
  
  private ScannerConfiguration(String id, String location, String resolution, Boolean camera, Boolean doubleScan) {
    this.id = id;
    this.location = location;
    this.resolution = resolution;
    this.camera = camera;
    this.doubleScan = doubleScan;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ScannerConfiguration scannerConfiguration = (ScannerConfiguration) obj;
      return ObjectsCompat.equals(getId(), scannerConfiguration.getId()) &&
              ObjectsCompat.equals(getLocation(), scannerConfiguration.getLocation()) &&
              ObjectsCompat.equals(getResolution(), scannerConfiguration.getResolution()) &&
              ObjectsCompat.equals(getCamera(), scannerConfiguration.getCamera()) &&
              ObjectsCompat.equals(getDoubleScan(), scannerConfiguration.getDoubleScan());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLocation())
      .append(getResolution())
      .append(getCamera())
      .append(getDoubleScan())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ScannerConfiguration {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("resolution=" + String.valueOf(getResolution()) + ", ")
      .append("camera=" + String.valueOf(getCamera()) + ", ")
      .append("doubleScan=" + String.valueOf(getDoubleScan()))
      .append("}")
      .toString();
  }
  
  public static LocationStep builder() {
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
  public static ScannerConfiguration justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new ScannerConfiguration(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      location,
      resolution,
      camera,
      doubleScan);
  }
  public interface LocationStep {
    ResolutionStep location(String location);
  }
  

  public interface ResolutionStep {
    CameraStep resolution(String resolution);
  }
  

  public interface CameraStep {
    DoubleScanStep camera(Boolean camera);
  }
  

  public interface DoubleScanStep {
    BuildStep doubleScan(Boolean doubleScan);
  }
  

  public interface BuildStep {
    ScannerConfiguration build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements LocationStep, ResolutionStep, CameraStep, DoubleScanStep, BuildStep {
    private String id;
    private String location;
    private String resolution;
    private Boolean camera;
    private Boolean doubleScan;
    @Override
     public ScannerConfiguration build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ScannerConfiguration(
          id,
          location,
          resolution,
          camera,
          doubleScan);
    }
    
    @Override
     public ResolutionStep location(String location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }
    
    @Override
     public CameraStep resolution(String resolution) {
        Objects.requireNonNull(resolution);
        this.resolution = resolution;
        return this;
    }
    
    @Override
     public DoubleScanStep camera(Boolean camera) {
        Objects.requireNonNull(camera);
        this.camera = camera;
        return this;
    }
    
    @Override
     public BuildStep doubleScan(Boolean doubleScan) {
        Objects.requireNonNull(doubleScan);
        this.doubleScan = doubleScan;
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
    private CopyOfBuilder(String id, String location, String resolution, Boolean camera, Boolean doubleScan) {
      super.id(id);
      super.location(location)
        .resolution(resolution)
        .camera(camera)
        .doubleScan(doubleScan);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder resolution(String resolution) {
      return (CopyOfBuilder) super.resolution(resolution);
    }
    
    @Override
     public CopyOfBuilder camera(Boolean camera) {
      return (CopyOfBuilder) super.camera(camera);
    }
    
    @Override
     public CopyOfBuilder doubleScan(Boolean doubleScan) {
      return (CopyOfBuilder) super.doubleScan(doubleScan);
    }
  }
  
}
