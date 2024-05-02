package com.willy.malltest.service.impl;

import com.willy.malltest.dto.TrackDTO;
import com.willy.malltest.dto.TrackShowDTO;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.Track;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.ProductSpecRepository;
import com.willy.malltest.repository.TrackRepository;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTrackDTOs() {
        List<Track> tracks = trackRepository.findAll();
        List<TrackDTO> tracksdto = new ArrayList<>();
        for (Track track : tracks) {
            TrackDTO dto = new TrackDTO();
            dto.setTrackID(track.getTrackID());
            dto.setUserID(track.getUser().getUserId());
            dto.setSpecID(track.getProductSpec().getSpecId());
            tracksdto.add(dto);
        }
        return tracksdto;
    }

    @Override
    public List<TrackShowDTO> getShowTrackDTOs(Long userId) {
        List<Track> tracks = usersRepository.findById(userId).get().getTrack();
        List<TrackShowDTO> trackShowDTOs = new ArrayList<>();
        for (Track track : tracks) {
            TrackShowDTO dto = new TrackShowDTO();
            dto.setTrackID(track.getTrackID());
            dto.setUserID(track.getUser().getUserId());
            dto.setSpecID(track.getProductSpec().getSpecId());
            dto.setProductId(track.getProductSpec().getProduct().getProductId());
            List<ProductPhoto> productPhotos = track.getProductSpec().getProductPhotos();
            if (!productPhotos.isEmpty()) {
                ProductPhoto productPhoto = productPhotos.get(0);
                String photoBase64 = Base64.getEncoder().encodeToString(productPhoto.getPhotoFile());
                dto.setPhotoFile(photoBase64);

            }
            dto.setProductName(track.getProductSpec().getProduct().getProductName());
            dto.setProductPrice(track.getProductSpec().getProduct().getPrice());
            dto.setProductDescription(track.getProductSpec().getProduct().getProductDescription());
            List<String> specIds = new ArrayList<>();
            specIds.add(track.getProductSpec().getSpecId());
            dto.setSpecIds(specIds);
            trackShowDTOs.add(dto);
        }

        return trackShowDTOs;
    }

    @Override
    @Transactional
    public boolean getCheckTrackDTO(Long userId, List<String> specId) {
        for (String s : specId) {
            Track tracks = trackRepository.findTrackByspecIdAnduserId(s,userId);
            if(tracks!=null){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean addTrack(String specID, Long userId) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        ProductSpec productSpec = productSpecRepository.findById(specID).orElse(null);
        if (productSpec == null) {
            throw new IllegalArgumentException("ProductSpec with ID " + specID + " not found");
        }

        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack != null) {
            throw new IllegalStateException("Track already exists");
        }
        Track newTrack = new Track();
        newTrack.setUser(user);
        newTrack.setProductSpec(productSpec);
        trackRepository.save(newTrack);
        return true;
    }

    @Override
    @Transactional
    public void deleteTrack(String specID, Long userId) {
        Track existingTrack = trackRepository.findTrackByspecIdAnduserId(specID, userId);
        if (existingTrack == null) {
            throw new IllegalStateException("Track does not exist");
        }
        trackRepository.delete(existingTrack);
    }

    public String getProductIdBySpecId(String specId) {
        return productSpecRepository.findProductIdBySpecId(specId)
                .orElseThrow(() -> new IllegalArgumentException("No product found for specId: " + specId));
    }
}

