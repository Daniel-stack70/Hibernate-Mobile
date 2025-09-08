package com.daniel;

import com.daniel.daos.GenericDAO;
import com.daniel.entities.*;
import com.daniel.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            GenericDAO<Mobile> mobileDAO = new GenericDAO<>(Mobile.class, em);

            // Create a Mobile
            Mobile mobile = new Mobile();
            mobile.setName("Galaxy Z Fold");
            mobile.setPrice(1599.0);

            // Profile
            Profile profile = new Profile();
            profile.setType("Premium");
            profile.setMobile(mobile);
            mobile.setProfile(profile);

            // Skills
            Skill s1 = new Skill();
            s1.setName("5G Support");
            s1.setMobile(mobile);

            Skill s2 = new Skill();
            s2.setName("Wireless Charging");
            s2.setMobile(mobile);

            mobile.setSkills(new ArrayList<>(Arrays.asList(s1, s2)));

            // Team
            Team team = new Team();
            team.setName("Launch Team");
            mobile.setTeams(new ArrayList<>(List.of(team)));
            team.setMobiles(new ArrayList<>(List.of(mobile)));

            // Save Mobile (cascades to Profile and Skills if cascade is set)
            mobileDAO.save(mobile);

            // Read Mobile
            Mobile loaded = mobileDAO.findById(mobile.getId());
            System.out.println("Loaded Mobile: " + loaded);

            // Update Mobile
            loaded.setPrice(1499.0);
            mobileDAO.update(loaded);

            loaded = mobileDAO.findById(mobile.getId());
            System.out.println("Updated Mobile: " + loaded);
            // Delete Mobile
            // mobileDAO.delete(loaded);

        } finally {
            em.close();
            HibernateUtil.close();
        }
    }
}
