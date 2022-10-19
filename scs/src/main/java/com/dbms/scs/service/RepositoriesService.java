package com.dbms.scs.service;

import com.dbms.scs.domain.Repositories;
import com.dbms.scs.domain.Student;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.RepositoriesDTO;
import com.dbms.scs.repos.RepositoriesRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.repos.VerticalRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RepositoriesService {

    private final RepositoriesRepository repositoriesRepository;
    private final StudentRepository studentRepository;
    private final VerticalRepository verticalRepository;

    public RepositoriesService(final RepositoriesRepository repositoriesRepository,
            final StudentRepository studentRepository,
            final VerticalRepository verticalRepository) {
        this.repositoriesRepository = repositoriesRepository;
        this.studentRepository = studentRepository;
        this.verticalRepository = verticalRepository;
    }

    public List<RepositoriesDTO> findAll() {
        return repositoriesRepository.findAll(Sort.by("repoID"))
                .stream()
                .map(repositories -> mapToDTO(repositories, new RepositoriesDTO()))
                .collect(Collectors.toList());
    }

    public RepositoriesDTO get(final Integer repoID) {
        return repositoriesRepository.findById(repoID)
                .map(repositories -> mapToDTO(repositories, new RepositoriesDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final RepositoriesDTO repositoriesDTO) {
        final Repositories repositories = new Repositories();
        mapToEntity(repositoriesDTO, repositories);
        return repositoriesRepository.save(repositories).getRepoID();
    }

    public void update(final Integer repoID, final RepositoriesDTO repositoriesDTO) {
        final Repositories repositories = repositoriesRepository.findById(repoID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(repositoriesDTO, repositories);
        repositoriesRepository.save(repositories);
    }

    public void delete(final Integer repoID) {
        repositoriesRepository.deleteById(repoID);
    }

    private RepositoriesDTO mapToDTO(final Repositories repositories,
            final RepositoriesDTO repositoriesDTO) {
        repositoriesDTO.setRepoID(repositories.getRepoID());
        repositoriesDTO.setYear(repositories.getYear());
        repositoriesDTO.setCompiledOn(repositories.getCompiledOn());
        repositoriesDTO.setRepoLink(repositories.getRepoLink());
        repositoriesDTO.setStudentCompilesrepos(repositories.getStudentCompilesrepos() == null ? null : repositories.getStudentCompilesrepos().getStudentRollNo());
        repositoriesDTO.setVerticalRepos(repositories.getVerticalRepos() == null ? null : repositories.getVerticalRepos().getVerticalID());
        return repositoriesDTO;
    }

    private Repositories mapToEntity(final RepositoriesDTO repositoriesDTO,
            final Repositories repositories) {
        repositories.setYear(repositoriesDTO.getYear());
        repositories.setCompiledOn(repositoriesDTO.getCompiledOn());
        repositories.setRepoLink(repositoriesDTO.getRepoLink());
        final Student studentCompilesrepos = repositoriesDTO.getStudentCompilesrepos() == null ? null : studentRepository.findById(repositoriesDTO.getStudentCompilesrepos())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studentCompilesrepos not found"));
        repositories.setStudentCompilesrepos(studentCompilesrepos);
        final Vertical verticalRepos = repositoriesDTO.getVerticalRepos() == null ? null : verticalRepository.findById(repositoriesDTO.getVerticalRepos())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "verticalRepos not found"));
        repositories.setVerticalRepos(verticalRepos);
        return repositories;
    }

}
