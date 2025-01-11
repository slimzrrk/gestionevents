package com.polytec.gestionevents.Services;

import com.polytec.gestionevents.Entities.Participant;

import java.util.List;

public interface IServiceParticipant {
    void addParticipant(Participant participant);
    Participant getParticipant(Long id);
    List<Participant> getAllParticipants();
    void updateParticipant(Participant participant);
    void deleteParticipant(Long id);
}
