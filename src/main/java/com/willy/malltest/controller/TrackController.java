package com.willy.malltest.controller;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.dto.TrackDeDTO;
import com.willy.malltest.dto.TrackShowDTO;
import com.willy.malltest.model.Track;
import com.willy.malltest.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173", "http://localhost:3000"})

public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/track")
    public List<TrackDTO> getAllTrack() {
        return trackService.getAllTrackDTOs();
    }

    @GetMapping("/getshow/track")
    public List<TrackShowDTO> getShowTrackDTOs(@RequestParam Long userId) {
        return trackService.getShowTrackDTOs(userId);
    }

    @PostMapping("/check/track")
    public boolean getShowTrackDTOs(@RequestBody TrackDeDTO trackDeDTO) {
        Long userId = trackDeDTO.getUserID();
        List<String> specId = trackDeDTO.getSpecID();
        return trackService.getCheckTrackDTO(userId, specId);
    }

    @PostMapping("/create/track")
    public boolean creatTrack(@RequestBody TrackDeDTO trackDeDTO) {
        Long userId = trackDeDTO.getUserID();
        List<String> specId = trackDeDTO.getSpecID();
        String specID = specId.get(0);
        return trackService.addTrack(specID, userId);
    }

    @DeleteMapping("/delete/track")
    public void deleteTrack(@RequestBody TrackDTO trackDTO) {
        Long userId = trackDTO.getUserID();
        String specID = trackDTO.getSpecID();
        trackService.deleteTrack(specID, userId);
    }

    @DeleteMapping("/Dedelete/track")
    public void DedeleteTrack(@RequestBody TrackDeDTO trackDeDTO) {
        System.out.println(trackDeDTO);
        Long userId = trackDeDTO.getUserID();
        List<String> specId = trackDeDTO.getSpecID();
        System.out.println(specId);
        String specID = specId.get(0);
        System.out.println(userId);
        System.out.println(specID);
        trackService.deleteTrack(specID, userId);
    }
}
