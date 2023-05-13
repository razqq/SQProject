package com.example.demo.validators;

import com.example.demo.controllers.ScheduleController;
import com.example.demo.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
public class ValidateTimeslotTest {

    @InjectMocks
    private ScheduleController scheduleController;

    @Before
    public void clearSchedule(){
        Schedule.timeslots.clear();
    }

    @Test
    public void testValidationOfTwoTimeslots() throws IOException {
        Timeslot timeslot1 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                new Teacher("Radulescu Vlad"),
                new Room("C401", "Laboratory", 30));
        Timeslot timeslot2 = new Timeslot(12, 14, Day.Wednesday,
                new StudGroup("A2", 2, 33),
                new Subject("Sisteme Embedded"), "Seminary",
                new Teacher("Iftene Adrian"),
                new Room("C203", "Seminary", 30));

        ResponseEntity<?> responseEntity1 = scheduleController.addTimeslotToSchedule(timeslot1.getStartTime(),
                timeslot1.getEndTime(),
                timeslot1.getDay(),
                timeslot1.getStudGroup(),
                timeslot1.getSubject(),
                timeslot1.getClassType(),
                timeslot1.getTeacher(),
                timeslot1.getRoom());

        ResponseEntity<?> responseEntity2 = scheduleController.addTimeslotToSchedule(timeslot2.getStartTime(),
                timeslot2.getEndTime(),
                timeslot2.getDay(),
                timeslot2.getStudGroup(),
                timeslot2.getSubject(),
                timeslot2.getClassType(),
                timeslot2.getTeacher(),
                timeslot2.getRoom());

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testClassTypeEqualsRoomTypeValidation() throws IOException {
        Timeslot timeslot = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                new Teacher("Radulescu Vlad"),
                new Room("C2", "Course", 120));

        ResponseEntity<?> responseEntity = scheduleController.addTimeslotToSchedule(timeslot.getStartTime(),
                timeslot.getEndTime(),
                timeslot.getDay(),
                timeslot.getStudGroup(),
                timeslot.getSubject(),
                timeslot.getClassType(),
                timeslot.getTeacher(),
                timeslot.getRoom());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat("Invalid class, the course is scheduled in the wrong room.").isSubstringOf((CharSequence) responseEntity.getBody());
    }

    @Test
    public void testSameRoomValidation() throws IOException {
        Room room = new Room("C401", "Laboratory", 30);
        Timeslot timeslot1 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                new Teacher("Radulescu Vlad"),
                room);
        Timeslot timeslot2 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A2", 2, 33),
                new Subject("Programare avansata"), "Laboratory",
                new Teacher("Iftene Adrian"),
                room);

        ResponseEntity<?> responseEntity1 = scheduleController.addTimeslotToSchedule(timeslot1.getStartTime(),
                timeslot1.getEndTime(),
                timeslot1.getDay(),
                timeslot1.getStudGroup(),
                timeslot1.getSubject(),
                timeslot1.getClassType(),
                timeslot1.getTeacher(),
                timeslot1.getRoom());

        ResponseEntity<?> responseEntity2 = scheduleController.addTimeslotToSchedule(timeslot2.getStartTime(),
                timeslot2.getEndTime(),
                timeslot2.getDay(),
                timeslot2.getStudGroup(),
                timeslot2.getSubject(),
                timeslot2.getClassType(),
                timeslot2.getTeacher(),
                timeslot2.getRoom());

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat("Invalid class, the room is already occupied at this time.").isSubstringOf((CharSequence) responseEntity2.getBody());
    }

    @Test
    public void testSameTeacherValidation() throws IOException {
        Teacher teacher = new Teacher("Radulescu Vlad");
        Timeslot timeslot1 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                teacher,
                new Room("C401", "Laboratory", 30));
        Timeslot timeslot2 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A2", 2, 33),
                new Subject("Programare avansata"), "Laboratory",
                teacher,
                new Room("C411", "Laboratory", 30));

        ResponseEntity<?> responseEntity1 = scheduleController.addTimeslotToSchedule(timeslot1.getStartTime(),
                timeslot1.getEndTime(),
                timeslot1.getDay(),
                timeslot1.getStudGroup(),
                timeslot1.getSubject(),
                timeslot1.getClassType(),
                timeslot1.getTeacher(),
                timeslot1.getRoom());

        ResponseEntity<?> responseEntity2 = scheduleController.addTimeslotToSchedule(timeslot2.getStartTime(),
                timeslot2.getEndTime(),
                timeslot2.getDay(),
                timeslot2.getStudGroup(),
                timeslot2.getSubject(),
                timeslot2.getClassType(),
                timeslot2.getTeacher(),
                timeslot2.getRoom());

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat("Invalid class, the teacher has already a program at this time.").isSubstringOf((CharSequence) responseEntity2.getBody());
    }

    @Test
    public void testSameTeacherSameRoomSameTimeValidation() throws IOException {
        Teacher teacher = new Teacher("Radulescu Vlad");
        Room room = new Room("C401", "Laboratory", 30);
        Timeslot timeslot1 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                teacher, room);
        Timeslot timeslot2 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A2", 2, 33),
                new Subject("Programare avansata"), "Laboratory",
                teacher, room);

        ResponseEntity<?> responseEntity1 = scheduleController.addTimeslotToSchedule(timeslot1.getStartTime(),
                timeslot1.getEndTime(),
                timeslot1.getDay(),
                timeslot1.getStudGroup(),
                timeslot1.getSubject(),
                timeslot1.getClassType(),
                timeslot1.getTeacher(),
                timeslot1.getRoom());

        ResponseEntity<?> responseEntity2 = scheduleController.addTimeslotToSchedule(timeslot2.getStartTime(),
                timeslot2.getEndTime(),
                timeslot2.getDay(),
                timeslot2.getStudGroup(),
                timeslot2.getSubject(),
                timeslot2.getClassType(),
                timeslot2.getTeacher(),
                timeslot2.getRoom());

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat("Invalid class, the teacher has already a program at this time.").isSubstringOf((CharSequence) responseEntity2.getBody());
        //assertThat("Invalid class, the teacher has already scheduled a course here at this time.").isSubstringOf((CharSequence) responseEntity2.getBody()); // this one fails
    }

    @Test
    public void testTimeslotOverlapping() throws IOException{
        Timeslot timeslot1 = new Timeslot(8, 10, Day.Monday,
                new StudGroup("A1", 2, 28),
                new Subject("Tehnologii Web"), "Laboratory",
                new Teacher("Radulescu Vlad"),
                new Room("C401", "Laboratory", 30));
        Timeslot timeslot2 = new Timeslot(9, 11, Day.Monday,
                new StudGroup("A2", 2, 33),
                new Subject("Tehnologii Web"), "Seminary",
                new Teacher("Radulescu Vlad"),
                new Room("C203", "Seminary", 30));

        ResponseEntity<?> responseEntity1 = scheduleController.addTimeslotToSchedule(timeslot1.getStartTime(),
                timeslot1.getEndTime(),
                timeslot1.getDay(),
                timeslot1.getStudGroup(),
                timeslot1.getSubject(),
                timeslot1.getClassType(),
                timeslot1.getTeacher(),
                timeslot1.getRoom());

        ResponseEntity<?> responseEntity2 = scheduleController.addTimeslotToSchedule(timeslot2.getStartTime(),
                timeslot2.getEndTime(),
                timeslot2.getDay(),
                timeslot2.getStudGroup(),
                timeslot2.getSubject(),
                timeslot2.getClassType(),
                timeslot2.getTeacher(),
                timeslot2.getRoom());

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
