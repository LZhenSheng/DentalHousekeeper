package com.example.demo.mapper;

import com.example.demo.domin.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalMapper {

    /**
     * 保存用户
     * @param data
     * @return
     */
    int create(Hospital data);

    int updateHospital(Hospital data);

    List<Hospital> loginHospital(Hospital data);


    int addDoctor(Doctor data);

    List<Doctor> findDoctorsByHospitalId(Hospital data);

    Doctor findDoctorByDoctorId(String id);

    int updateDoctorMessage(Doctor data);

    int deleteDoctorMessage(Doctor data);

    int createPreOrderByHospital(PreOrder data);

    List<PreOrder> getPreOrderByDoctorId(Doctor data);

    int deletePreOrderByPreOrderId(PreOrder data);

    List<Hospital> findAllHospital();

    Hospital findHospitalByHospitalId(Hospital data);

    List<Hospital> loginDoctor(Doctor data);

    List<AppointMent> findAppointMentByDoctorId(Doctor data);

    Patient findPatientsByPatientId(String data);

    int createDICOMImage(DICOMImage data);

    List<DICOMImage> findDicomByPatientId(DICOMImage data);

    DICOMImage findDicomByDicomId(DICOMImage data);

    int createERecord(EReocrd data);

    void updateAppointMentStatus(String data);

    List<EReocrd> findERecordByPatientId(Patient data);

    EReocrd findERecordById(EReocrd data);

    List<EReocrd> findERecordByDoctorId(Doctor data);

    List<DICOMImage> findDicomsByDoctorId(Doctor data);

    List<PreOrder> findSchedulingsByHospitalId(Hospital data);

    List<String> finddoctorIdByName(String data);

    int addDepartment(Department data);

    List<Department> findDepartmentsByHospitalId(Hospital data);

    Department findDepartmentByDepartmentId(String id);

    int updateDepartmentByDepartmentId(Department data);

    int deleteDepartmentByDepartmentId(Department data);

    List<Payment> findPaymentsByHospitalId(Hospital data);

    int updateHospitalMessage(Hospital data);
}
