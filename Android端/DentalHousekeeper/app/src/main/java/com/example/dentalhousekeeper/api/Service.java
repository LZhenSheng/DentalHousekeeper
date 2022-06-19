package com.example.dentalhousekeeper.api;

import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.BaseModel;
import com.example.dentalhousekeeper.domin.DICOMImage;
import com.example.dentalhousekeeper.domin.Department;
import com.example.dentalhousekeeper.domin.DetailResponse;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.EReocrd;
import com.example.dentalhousekeeper.domin.GroupConsultation;
import com.example.dentalhousekeeper.domin.Hospital;
import com.example.dentalhousekeeper.domin.KV;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.Pay;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.domin.Session;

import org.dcm4che3.android.imageio.dicom.DicomImageReader;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    /***
     * 医生注册
     * @param patient
     * @return
     */
    @POST("registerDoctor")
    Observable<DetailResponse<BaseModel>> registerAccount(@Body Doctor patient);

    /***
     * 病人注册
     * @param patient
     * @return
     */
    @POST("registerPatient")
    Observable<DetailResponse<BaseModel>> registerAccount(@Body Patient patient);

    /***
     * 病人注册
     * @param patient
     * @return
     */
    @POST("request_sms_code")
    Observable<DetailResponse<BaseModel>> sendSMSCode(@Body String patient);


    /***
     * 病人登录
     * @param patient
     * @return
     */
    @POST("login_patient")
    Observable<DetailResponse<Session>> loginPatient(@Body Patient patient);

    /***
     * 医生登录
     * @param patient
     * @return
     */
    @POST("login_doctor")
    Observable<DetailResponse<Session>> loginDoctor(@Body Doctor patient);


    /***
     * 医生登录
     * @param date
     * @return
     */
    @POST("setPreorder")
    Observable<DetailResponse<BaseModel>> createPreorder(@Body PreOrder date);

    /***
     * 医生登录
     * @param date
     * @return
     */
    @POST("updateDoctorMessage")
    Observable<DetailResponse<Boolean>> updateMessag(@Body Doctor date);

    /***
     * 医生登录
     * @param date
     * @return
     */
    @POST("updatePatientMessage")
    Observable<DetailResponse<Boolean>> updateMessag(@Body Patient date);


    /***
     * 医生登录
     * @return
     */
    @POST("findHospital")
    Observable<List<String>> findHospital();

    /***
     * 医生登录
     * @return
     */
    @POST("findDoctor")
    Observable<List<String>> findDoctor(@Body Doctor data);

    /***
     * 医生登录
     * @return
     */
    @POST("findPreorder")
    Observable<PreOrder> findPreorder(@Body PreOrder data);

    @POST("createAppointment")
    Observable<Pay> createAppointment(@Body AppointMent appointMent);

    @POST("findAppointment")
    Observable<List<AppointMent>> findAppointment(@Body AppointMent appointMent);

    @POST("findDoctorById")
    Observable<Doctor> findDoctorById(@Body Doctor doctor);

    @POST("findPreorderById")
    Observable<PreOrder> findPreorderById(@Body PreOrder data);

    @POST("findPatientById")
    Observable<Patient> findPatientById(@Body Patient data);

    @POST("findPreorderByPatientId")
    Observable<List<Integer>> findPreorderByPatientId(@Body Patient data);

    @POST("findPreorderByDoctorId")
    Observable<List<Integer>> findPreorderByDoctorId(@Body Doctor data);

    @POST("findAppointmentByDoctorId")
    Observable<List<AppointMent>> findAppointmentByDoctorId(@Body Doctor data);

    @POST("findAppointmentByPatientId")
    Observable<List<AppointMent>> findAppointmentByPatientId(@Body Patient data);

    @POST("findAllHospital")
    Observable<List<Hospital>> findAllHospital();

    @POST("findDoctorssByHospitalId")
    Observable<List<Doctor>> findDoctorsByHospitalId(@Body Hospital data);

    @POST("findDoctorssByDoctorId")
    Observable<Doctor> findDoctorssByDoctorId(@Body Doctor data);

    @POST("getPreOrdersByDoctorId")
    Observable<List<PreOrder>> getPreOrdersByDoctorId(@Body Doctor data);

    @POST("findHospitalByHospitalId")
    Observable<Hospital> findHospitalByHospitalId(@Body Hospital data);

    @POST("findDoctorByPhone")
    Observable<Doctor> findDoctorByPhone(@Body Doctor data);

    @POST("findPatientByPhone")
    Observable<Patient> findPatientByPhone(@Body Patient data);

    @POST("findMessageByPhone")
    Observable<KV> findMessageByPhone(@Body String data);

    @POST("getQ")
    Observable<Patient> getQ();

    @POST("tradeQuery")
    Observable<AppointMent> tradeQuery(@Body AppointMent data);

    @POST("findDicomsByPatientId")
    Observable<List<DICOMImage>> findDicomsByPatientId(@Body DICOMImage data);

    @POST("findDicoByDicomId")
    Observable<DICOMImage> findDicoByPatientId(@Body DICOMImage data);

    @POST("findERecordByPatientId")
    Observable<List<EReocrd>> findERecordByPatientId(@Body Patient data);

    @POST("findERecordById")
    Observable<EReocrd> findERecordById(@Body EReocrd data);

    @POST("findERecordByDoctorId")
    Observable<List<EReocrd>> findERecordByDoctorId(@Body Doctor data);

    @POST("findDicomsByDoctorId")
    Observable<List<DICOMImage>> findDicomsByDoctorId(@Body Doctor data);

    @POST("getW")
    Observable<Pay> getW(@Body Doctor data);

    @POST("createGroupConsultation")
    Observable<Integer> createGroupConsultation(@Body GroupConsultation data);

    @POST("findGroupConsultations")
    Observable<List<GroupConsultation>> findGroupConsultations(@Body GroupConsultation data);

    @POST("findDepartmentssByHospitalId")
    Observable<List<Department>> findDepartmentsByHospitalId(@Body Hospital data);
}
