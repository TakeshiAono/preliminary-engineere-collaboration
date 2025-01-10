package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.ResponseSkill;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.repositories.SkillRepository;
import com.api.EngineerCollabo.services.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SkillControllerTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillService skillService;

    @InjectMocks
    private SkillController skillController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResponseSkills() {
        // モックの設定
        Skill skill1 = new Skill();
        skill1.setId(1);
        skill1.setName("Java");

        Skill skill2 = new Skill();
        skill2.setId(2);
        skill2.setName("Spring");

        List<Skill> skills = Arrays.asList(skill1, skill2);
        when(skillRepository.findAll()).thenReturn(skills);
        when(skillService.changeResponseSkill(skill1)).thenReturn(new ResponseSkill());
        when(skillService.changeResponseSkill(skill2)).thenReturn(new ResponseSkill());

        // 実行
        List<ResponseSkill> responseSkills = skillController.ResponseSkills();

        // 検証
        assertEquals(2, responseSkills.size());
    }

    @Test
    void testCreateSkill() {
        // モックの設定
        Skill requestSkill = new Skill();
        requestSkill.setName("Java");
        requestSkill.setCountLog(1);
        requestSkill.setUserId(1);

        // 実行
        skillController.createSkill(requestSkill);

        // 検証
        verify(skillService, times(1)).createSkill("Java", 1, 1);
    }

    @Test
    void testResponseSkill() {
        // モックの設定
        int skillId = 1;
        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName("Java");

        when(skillRepository.findById(skillId)).thenReturn(skill);
        when(skillService.changeResponseSkill(skill)).thenReturn(new ResponseSkill());

        // 実行
        ResponseSkill responseSkill = skillController.responseSkill(Optional.of(skillId));

        // 検証
        assertEquals(new ResponseSkill(), responseSkill);
    }

    @Test
    void testPutSkill() {
        // モックの設定
        int skillId = 1;
        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName("Java");

        Skill requestSkill = new Skill();
        requestSkill.setName("Python");
        requestSkill.setCountLog(2);

        when(skillRepository.findById(skillId)).thenReturn(skill);

        // 実行
        skillController.putSkill(Optional.of(skillId), requestSkill);

        // 検証
        assertEquals("Python", skill.getName());
        assertEquals(2, skill.getCountLog());
        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    void testDeleteOffer() {
        // モックの設定
        int skillId = 1;

        // 実行
        skillController.deleteOffer(Optional.of(skillId));

        // 検証
        verify(skillRepository, times(1)).deleteById(skillId);
    }
}
