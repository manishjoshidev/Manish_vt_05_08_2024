package com.mani.service;

import com.mani.Exception.BadRequestException;
import com.mani.entity.Redirect;
import com.mani.repository.RedirectRepository;
import com.mani.request.redirectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RedirectService {
    private final RedirectRepository redirectRepository;

    @Autowired
    public RedirectService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public Optional<Redirect> createRedirect(redirectRequest redirectRequest) {
        if (redirectRepository.existsByAlias(redirectRequest.getAlias())) {
            throw new BadRequestException("Alias already exists");
        }
        LocalDateTime expiryDate = LocalDateTime.now().plusMonths(10);
        Redirect redirect = new Redirect(redirectRequest.getAlias(), redirectRequest.getUrl(), expiryDate);
        redirect = redirectRepository.save(redirect);
        return Optional.ofNullable(redirect);
    }

    public Optional<Redirect> getRedirect(String alias) {
        return redirectRepository.findByAlias(alias);
    }

    public boolean updateRedirect(String alias, String newUrl) {
        Optional<Redirect> optionalRedirect = redirectRepository.findByAlias(alias);
        if (optionalRedirect.isPresent()) {
            Redirect redirect = optionalRedirect.get();
            redirect.setUrl(newUrl);
            redirectRepository.save(redirect);
            return true;
        }
        return false;
    }

    public boolean updateExpiry(String alias, int daysToAdd) {
        Optional<Redirect> optionalRedirect = redirectRepository.findByAlias(alias);
        if (optionalRedirect.isPresent()) {
            Redirect redirect = optionalRedirect.get();
            redirect.setExpiryDate(redirect.getExpiryDate().plusDays(daysToAdd));
            redirectRepository.save(redirect);
            return true;
        }
        return false;
    }
}
