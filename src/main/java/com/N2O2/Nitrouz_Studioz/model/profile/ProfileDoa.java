package com.N2O2.Nitrouz_Studioz.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProfileDoa extends JpaRepository<ProfileEntity, Long> {

    @Modifying
    @Query("UPDATE ProfileEntity SET profileName = ?1, first_name = ?2, last_name = ?3, email = ?4, about = ?5, phone = ?6 WHERE id = ?7")
    public void updateProfile(String profileName, String first_name, String last_name, String email, String about, String phone, Long id);

    public ProfileEntity findByEmail(String email);

    public ProfileEntity getById(Long id);

    public List<ProfileEntity> findAll();
}
