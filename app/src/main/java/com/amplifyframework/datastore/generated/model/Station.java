package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

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

/** This is an auto generated class representing the Station type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Stations")
public final class Station implements Model, Serializable {
  public static final QueryField ID = field("id");
  public static final QueryField STATION_NUMBER = field("stationNumber");
  public static final QueryField DATE = field("date");
  public static final QueryField CANCELLED = field("cancelled");
  public static final QueryField NOTES = field("notes");
  public static final QueryField STATE = field("state");
  public static final QueryField SCANNER_CONFIGURATION = field("stationScannerConfigurationId");
  public static final QueryField MAP_POINT = field("mapPoint");
  public static final QueryField BATTERY = field("battery");
  public static final QueryField TAKEN_PICTURES = field("takenPictures");
  public static final QueryField VOICE_RECORDINGS = field("voiceRecordings");
  public static final QueryField END_OF_ROUTINE = field("endOfRoutine");
  public static final QueryField LEICA_SCANNING_PROJECT = field("stationLeicaScanningProjectId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int", isRequired = true) Integer stationNumber;
  private final @ModelField(targetType="String", isRequired = true) String date;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean cancelled;
  private final @ModelField(targetType="ID") List<String> notes;
  private final @ModelField(targetType="ID") List<String> state;
  private final @ModelField(targetType="ScannerConfiguration") @BelongsTo(targetName = "stationScannerConfigurationId", type = ScannerConfiguration.class) ScannerConfiguration scannerConfiguration;
  private final @ModelField(targetType="Int") List<Integer> mapPoint;
  private final @ModelField(targetType="Int") Integer battery;
  private final @ModelField(targetType="Int") Integer takenPictures;
  private final @ModelField(targetType="Int") Integer voiceRecordings;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean endOfRoutine;
  private final @ModelField(targetType="LeicaScanningProject") @BelongsTo(targetName = "stationLeicaScanningProjectId", type = LeicaScanningProject.class) LeicaScanningProject leicaScanningProject;
  public String getId() {
      return id;
  }
  
  public Integer getStationNumber() {
      return stationNumber;
  }
  
  public String getDate() {
      return date;
  }
  
  public Boolean getCancelled() {
      return cancelled;
  }
  
  public List<String> getNotes() {
      return notes;
  }
  
  public List<String> getState() {
      return state;
  }
  
  public ScannerConfiguration getScannerConfiguration() {
      return scannerConfiguration;
  }
  
  public List<Integer> getMapPoint() {
      return mapPoint;
  }
  
  public Integer getBattery() {
      return battery;
  }
  
  public Integer getTakenPictures() {
      return takenPictures;
  }
  
  public Integer getVoiceRecordings() {
      return voiceRecordings;
  }
  
  public Boolean getEndOfRoutine() {
      return endOfRoutine;
  }
  
  public LeicaScanningProject getLeicaScanningProject() {
      return leicaScanningProject;
  }
  
  private Station(String id, Integer stationNumber, String date, Boolean cancelled, List<String> notes, List<String> state, ScannerConfiguration scannerConfiguration, List<Integer> mapPoint, Integer battery, Integer takenPictures, Integer voiceRecordings, Boolean endOfRoutine, LeicaScanningProject leicaScanningProject) {
    this.id = id;
    this.stationNumber = stationNumber;
    this.date = date;
    this.cancelled = cancelled;
    this.notes = notes;
    this.state = state;
    this.scannerConfiguration = scannerConfiguration;
    this.mapPoint = mapPoint;
    this.battery = battery;
    this.takenPictures = takenPictures;
    this.voiceRecordings = voiceRecordings;
    this.endOfRoutine = endOfRoutine;
    this.leicaScanningProject = leicaScanningProject;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Station station = (Station) obj;
      return ObjectsCompat.equals(getId(), station.getId()) &&
              ObjectsCompat.equals(getStationNumber(), station.getStationNumber()) &&
              ObjectsCompat.equals(getDate(), station.getDate()) &&
              ObjectsCompat.equals(getCancelled(), station.getCancelled()) &&
              ObjectsCompat.equals(getNotes(), station.getNotes()) &&
              ObjectsCompat.equals(getState(), station.getState()) &&
              ObjectsCompat.equals(getScannerConfiguration(), station.getScannerConfiguration()) &&
              ObjectsCompat.equals(getMapPoint(), station.getMapPoint()) &&
              ObjectsCompat.equals(getBattery(), station.getBattery()) &&
              ObjectsCompat.equals(getTakenPictures(), station.getTakenPictures()) &&
              ObjectsCompat.equals(getVoiceRecordings(), station.getVoiceRecordings()) &&
              ObjectsCompat.equals(getEndOfRoutine(), station.getEndOfRoutine()) &&
              ObjectsCompat.equals(getLeicaScanningProject(), station.getLeicaScanningProject());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getStationNumber())
      .append(getDate())
      .append(getCancelled())
      .append(getNotes())
      .append(getState())
      .append(getScannerConfiguration())
      .append(getMapPoint())
      .append(getBattery())
      .append(getTakenPictures())
      .append(getVoiceRecordings())
      .append(getEndOfRoutine())
      .append(getLeicaScanningProject())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Station {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("stationNumber=" + String.valueOf(getStationNumber()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("cancelled=" + String.valueOf(getCancelled()) + ", ")
      .append("notes=" + String.valueOf(getNotes()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("scannerConfiguration=" + String.valueOf(getScannerConfiguration()) + ", ")
      .append("mapPoint=" + String.valueOf(getMapPoint()) + ", ")
      .append("battery=" + String.valueOf(getBattery()) + ", ")
      .append("takenPictures=" + String.valueOf(getTakenPictures()) + ", ")
      .append("voiceRecordings=" + String.valueOf(getVoiceRecordings()) + ", ")
      .append("endOfRoutine=" + String.valueOf(getEndOfRoutine()) + ", ")
      .append("leicaScanningProject=" + String.valueOf(getLeicaScanningProject()))
      .append("}")
      .toString();
  }
  
  public static StationNumberStep builder() {
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
  public static Station justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Station(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      stationNumber,
      date,
      cancelled,
      notes,
      state,
      scannerConfiguration,
      mapPoint,
      battery,
      takenPictures,
      voiceRecordings,
      endOfRoutine,
      leicaScanningProject);
  }
  public interface StationNumberStep {
    DateStep stationNumber(Integer stationNumber);
  }
  

  public interface DateStep {
    CancelledStep date(String date);
  }
  

  public interface CancelledStep {
    EndOfRoutineStep cancelled(Boolean cancelled);
  }
  

  public interface EndOfRoutineStep {
    BuildStep endOfRoutine(Boolean endOfRoutine);
  }
  

  public interface BuildStep {
    Station build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep notes(List<String> notes);
    BuildStep state(List<String> state);
    BuildStep scannerConfiguration(ScannerConfiguration scannerConfiguration);
    BuildStep mapPoint(List<Integer> mapPoint);
    BuildStep battery(Integer battery);
    BuildStep takenPictures(Integer takenPictures);
    BuildStep voiceRecordings(Integer voiceRecordings);
    BuildStep leicaScanningProject(LeicaScanningProject leicaScanningProject);
  }
  

  public static class Builder implements StationNumberStep, DateStep, CancelledStep, EndOfRoutineStep, BuildStep {
    private String id;
    private Integer stationNumber;
    private String date;
    private Boolean cancelled;
    private Boolean endOfRoutine;
    private List<String> notes;
    private List<String> state;
    private ScannerConfiguration scannerConfiguration;
    private List<Integer> mapPoint;
    private Integer battery;
    private Integer takenPictures;
    private Integer voiceRecordings;
    private LeicaScanningProject leicaScanningProject;
    @Override
     public Station build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Station(
          id,
          stationNumber,
          date,
          cancelled,
          notes,
          state,
          scannerConfiguration,
          mapPoint,
          battery,
          takenPictures,
          voiceRecordings,
          endOfRoutine,
          leicaScanningProject);
    }
    
    @Override
     public DateStep stationNumber(Integer stationNumber) {
        Objects.requireNonNull(stationNumber);
        this.stationNumber = stationNumber;
        return this;
    }
    
    @Override
     public CancelledStep date(String date) {
        Objects.requireNonNull(date);
        this.date = date;
        return this;
    }
    
    @Override
     public EndOfRoutineStep cancelled(Boolean cancelled) {
        Objects.requireNonNull(cancelled);
        this.cancelled = cancelled;
        return this;
    }
    
    @Override
     public BuildStep endOfRoutine(Boolean endOfRoutine) {
        Objects.requireNonNull(endOfRoutine);
        this.endOfRoutine = endOfRoutine;
        return this;
    }
    
    @Override
     public BuildStep notes(List<String> notes) {
        this.notes = notes;
        return this;
    }
    
    @Override
     public BuildStep state(List<String> state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep scannerConfiguration(ScannerConfiguration scannerConfiguration) {
        this.scannerConfiguration = scannerConfiguration;
        return this;
    }
    
    @Override
     public BuildStep mapPoint(List<Integer> mapPoint) {
        this.mapPoint = mapPoint;
        return this;
    }
    
    @Override
     public BuildStep battery(Integer battery) {
        this.battery = battery;
        return this;
    }
    
    @Override
     public BuildStep takenPictures(Integer takenPictures) {
        this.takenPictures = takenPictures;
        return this;
    }
    
    @Override
     public BuildStep voiceRecordings(Integer voiceRecordings) {
        this.voiceRecordings = voiceRecordings;
        return this;
    }
    
    @Override
     public BuildStep leicaScanningProject(LeicaScanningProject leicaScanningProject) {
        this.leicaScanningProject = leicaScanningProject;
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
    private CopyOfBuilder(String id, Integer stationNumber, String date, Boolean cancelled, List<String> notes, List<String> state, ScannerConfiguration scannerConfiguration, List<Integer> mapPoint, Integer battery, Integer takenPictures, Integer voiceRecordings, Boolean endOfRoutine, LeicaScanningProject leicaScanningProject) {
      super.id(id);
      super.stationNumber(stationNumber)
        .date(date)
        .cancelled(cancelled)
        .endOfRoutine(endOfRoutine)
        .notes(notes)
        .state(state)
        .scannerConfiguration(scannerConfiguration)
        .mapPoint(mapPoint)
        .battery(battery)
        .takenPictures(takenPictures)
        .voiceRecordings(voiceRecordings)
        .leicaScanningProject(leicaScanningProject);
    }
    
    @Override
     public CopyOfBuilder stationNumber(Integer stationNumber) {
      return (CopyOfBuilder) super.stationNumber(stationNumber);
    }
    
    @Override
     public CopyOfBuilder date(String date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder cancelled(Boolean cancelled) {
      return (CopyOfBuilder) super.cancelled(cancelled);
    }
    
    @Override
     public CopyOfBuilder endOfRoutine(Boolean endOfRoutine) {
      return (CopyOfBuilder) super.endOfRoutine(endOfRoutine);
    }
    
    @Override
     public CopyOfBuilder notes(List<String> notes) {
      return (CopyOfBuilder) super.notes(notes);
    }
    
    @Override
     public CopyOfBuilder state(List<String> state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder scannerConfiguration(ScannerConfiguration scannerConfiguration) {
      return (CopyOfBuilder) super.scannerConfiguration(scannerConfiguration);
    }
    
    @Override
     public CopyOfBuilder mapPoint(List<Integer> mapPoint) {
      return (CopyOfBuilder) super.mapPoint(mapPoint);
    }
    
    @Override
     public CopyOfBuilder battery(Integer battery) {
      return (CopyOfBuilder) super.battery(battery);
    }
    
    @Override
     public CopyOfBuilder takenPictures(Integer takenPictures) {
      return (CopyOfBuilder) super.takenPictures(takenPictures);
    }
    
    @Override
     public CopyOfBuilder voiceRecordings(Integer voiceRecordings) {
      return (CopyOfBuilder) super.voiceRecordings(voiceRecordings);
    }
    
    @Override
     public CopyOfBuilder leicaScanningProject(LeicaScanningProject leicaScanningProject) {
      return (CopyOfBuilder) super.leicaScanningProject(leicaScanningProject);
    }
  }
  
}
