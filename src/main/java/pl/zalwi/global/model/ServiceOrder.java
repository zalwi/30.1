package pl.zalwi.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String producer;
    String model;
    String registrationNumber;
    String vinNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate firstDateRegistration;
    Integer engineCapacity;

    @Enumerated(EnumType.STRING)
    FuelType fuelType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime creationDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime deadlineDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endDate;

    @OneToOne
    ContactPerson contactPerson;

    @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL)
    List<OrderTask> orderTaskList;

    private Boolean finished;

    public String basicInfo(){
        return producer + " " + model + " - " + registrationNumber + "\n" + contactPerson.firstName + " " + contactPerson.getLastName();
    }

    public String getDateTimeInSqlDateTimeFormat(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public Status checkStatus() {
        if (!finished) {
            if (deadlineDate.isBefore(LocalDateTime.now())) {
                return Status.DELAYED;
            } else {
                return Status.INPROGRESS;
            }
        } else {
            if (deadlineDate.isBefore(endDate)) {
                return Status.DONEAFTER;
            } else {
                return Status.DONE;
            }
        }
    }

    public String calculateDelay() {
        if (deadlineDate.equals(null)) {
            return "00h00m";
        }
        return getDifferenceBetweenDateTimeInHoursAndMinutes(deadlineDate, LocalDateTime.now());
    }

    public String calculateLeftTime() {
        if (endDate != null) {
            return "00h00m";
        }
        return getDifferenceBetweenDateTimeInHoursAndMinutes(LocalDateTime.now(), deadlineDate);
    }

    private String getDifferenceBetweenDateTimeInHoursAndMinutes(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        long minutes = ChronoUnit.MINUTES.between(firstDateTime, secondDateTime);
        long hours = ChronoUnit.HOURS.between(firstDateTime, secondDateTime);
        long minutesWithoutHours = minutes % 60;
        return hours + "h" + minutesWithoutHours + "m";
    }

    public void addNewTaskToList(OrderTask orderTask) {
        orderTaskList.add(orderTask);
        orderTask.setServiceOrder(this);
    }

    private int[] countTasks(){
        int newTasks = 0;
        int inProgressTasks = 0;
        int doneTasks = 0;
        int allTasks = 0;
        if(orderTaskList.isEmpty()){
            allTasks = 0;
        }else {
            allTasks = orderTaskList.size();
            for(OrderTask task: orderTaskList){
                switch(task.getTaskStatus()){
                    case NEW         -> {newTasks++;}
                    case INPROGRESS  -> {inProgressTasks++;}
                    case DONE        -> {doneTasks++;}
                }
            }
        }
                        /* [0]          [1]             [2]        [3] */
        return new int[]{newTasks, inProgressTasks, doneTasks, allTasks};
    }

    public String countAllTasks(){
        int[] counters = countTasks();
        return "Wykonane " + counters[2] + "/" + counters[3];
    }

    public String countNewTasks(){
        int[] counters = countTasks();
        return "Oczekujące " + counters[0];
    }

    public String countInPogressTasks(){
        int[] counters = countTasks();
        return "W trakcie " + counters[1];
    }
}
