package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
    private Scanner scanner;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public EmployeeMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void menuE(Calendar calendar) {
        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Check Patient by Name");
            System.out.println("2. View All Patients");
            System.out.println("3. View All Appointments");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Patient Profile");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    checkName(calendar);
                    break;
                case 2:
                    printPatientList(calendar);
                    break;
                case 3:
                    printAppointmentList(calendar);
                    break;
                case 4:
                    cancelAppointment(calendar);
                    break;
                case 5:
                    System.out.print("Enter patient username: ");
                    String username = scanner.nextLine();
                    Patient patient = findPatient(calendar, username);
                    if (patient != null) {
                        profilePatient(calendar, patient);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void checkName(Calendar calendar) {
        System.out.print("Enter patient name to search: ");
        String searchName = scanner.nextLine();
        boolean found = false;

        for (Patient patient : calendar.getPatientList()) {
            if (patient.getName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println(patient);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patients found with that name.");
        }
    }

    public void printPatientList(Calendar calendar) {
        List<Patient> patients = calendar.getPatientList();
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        System.out.println("\n=== Patient List ===");
        for (Patient patient : patients) {
            System.out.println("Name: " + patient.getName());
            System.out.println("Contact: " + patient.getTelephone());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("------------------------");
        }
    }

    public void printAppointmentList(Calendar calendar) {
        List<Appointment> appointments = calendar.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }

        System.out.println("\n=== Appointment List ===");
        for (Appointment appointment : appointments) {
            System.out.println("Patient: " + appointment.getPatient().getName());
            System.out.println("Date/Time: " + appointment.getDateTime().format(formatter));
            System.out.println("------------------------");
        }
    }

    public void cancelAppointment(Calendar calendar) {
        System.out.print("Enter patient username: ");
        String username = scanner.nextLine();
        Patient patient = findPatient(calendar, username);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        List<Appointment> appointments = calendar.getAppointments();
        boolean hasAppointments = false;

        System.out.println("\nAppointments for " + patient.getName() + ":");
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient)) {
                System.out.println("Date/Time: " + appointment.getDateTime().format(formatter));
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments found for this patient.");
            return;
        }

        System.out.print("Enter appointment date/time to cancel (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            calendar.removeAppointment(patient, dateTime);
            System.out.println("Appointment cancelled successfully.");
        } catch (Exception e) {
            System.out.println("Invalid date/time format or appointment not found.");
        }
    }

    public void profilePatient(Calendar calendar, Patient patient) {
        System.out.println("\n=== Patient Profile ===");
        System.out.println("Name: " + patient.getName());
        System.out.println("Username: " + patient.getUsername());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Telephone: " + patient.getTelephone());

        // Show upcoming appointments
        System.out.println("\nUpcoming Appointments:");
        boolean hasAppointments = false;
        for (Appointment appointment : calendar.getAppointments()) {
            if (appointment.getPatient().equals(patient) && 
                appointment.getDateTime().isAfter(LocalDateTime.now())) {
                System.out.println("Date/Time: " + appointment.getDateTime().format(formatter));
                hasAppointments = true;
            }
        }
        if (!hasAppointments) {
            System.out.println("No upcoming appointments.");
        }
    }

    private Patient findPatient(Calendar calendar, String username) {
        for (Patient patient : calendar.getPatientList()) {
            if (patient.getUsername().equals(username)) {
                return patient;
            }
        }
        return null;
    }
}
