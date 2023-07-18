package com.codevalor.demo.freeads.service;

import com.codevalor.demo.freeads.dto.request.AdRequest;
import com.codevalor.demo.freeads.model.Ad;
import com.codevalor.demo.freeads.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    public Ad createAd(AdRequest adRequest) {
        Ad ad = Ad.builder()
                .title(adRequest.title())
                .description(adRequest.description())
                .price(adRequest.price())
                .build();
        return adRepository.save(ad);
    }

    public List<Ad> findAllAds() {
        return this.adRepository.findAll();
    }

    public Ad verifyAd(String id) {
        Ad ad = adRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("Ad not found"));
        ad.setVerified(true);
        adRepository.save(ad);
        return ad;
    }
}
