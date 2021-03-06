package ru.otus.spring.hw.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw.kanban.domain.Stage;
import ru.otus.spring.hw.kanban.domain.Task;
import ru.otus.spring.hw.kanban.dto.TaskDTO;
import ru.otus.spring.hw.kanban.exceptions.StageNotFoundException;
import ru.otus.spring.hw.kanban.exceptions.TaskNotFoundException;
import ru.otus.spring.hw.kanban.repository.StageRepository;
import ru.otus.spring.hw.kanban.repository.TaskRepository;
import ru.otus.spring.hw.kanban.security.UserAccount;
import ru.otus.spring.hw.kanban.security.UserAccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    private final StageRepository stageRepository;

    private final TaskRepository taskRepository;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public TaskServiceImpl(StageRepository stageRepository, TaskRepository taskRepository, UserAccountRepository userAccountRepository) {
        this.stageRepository = stageRepository;
        this.taskRepository = taskRepository;
        this.userAccountRepository = userAccountRepository;
    }

    public List<TaskDTO> findAll() {
        return fillMoveLinks(taskRepository.findAll().stream().map(TaskDTO::fromTask).collect(Collectors.toList()));
    }

    public List<TaskDTO> findAllByStage(int stageId) {
        Stage stage = findStage(stageId);
        List<TaskDTO> list = taskRepository.findAllByStage(stage).stream().map(TaskDTO::fromTask).collect(Collectors.toList());

        return fillMoveLinks(list);
    }

    private List<TaskDTO> fillMoveLinks(List<TaskDTO> list) {
        // [_, 2, 3, 4, 5, _] обрабатываем середину списка
        for (int i = 1; i < list.size() - 1; i++) {
            list.get(i).previousId = list.get(i - 1).id;
            list.get(i).nextId = list.get(i + 1).id;
        }

        // обрабатываем края, если в массиве хотя бы два элемента.
        if (list.size() > 1) {
            TaskDTO first = list.get(0);
            TaskDTO second = list.get(1);
            first.nextId = second.id;

            TaskDTO last = list.get(list.size() - 1);
            TaskDTO prev_last = list.get(list.size() - 2);
            last.previousId = prev_last.id;
        }
        return list;

    }

    public TaskDTO find(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found."));
        return TaskDTO.fromTask(task);
    }

    @Transactional
    public TaskDTO create(TaskDTO newTaskDTO) {
        Task task = new Task();

        newTaskDTO.fillTask(task);
        if (newTaskDTO.stageId > 0) {
            Stage stage = findStage(newTaskDTO.stageId);
            task.setStage(  stage);
        }

        if (newTaskDTO.username != null) {
            UserAccount acc = userAccountRepository.findByUsername(newTaskDTO.username);
            if (acc != null) {
                task.setUser(acc);
            }
        }

        taskRepository.save(task);

        final Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        //         создать                 ObjectIdentity         для бизнес сущности
        final ObjectIdentity oid = new ObjectIdentityImpl(Task.class, task.getId());
        // создать пустой         ACL
        final MutableAcl acl = aclService.createAcl(oid);

        // определить владельца сущности и права пользователей
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        // обновить ACL в БД
        aclService.updateAcl(acl);

        return TaskDTO.fromTask(task);
    }

    @Autowired
    MutableAclService aclService;

    @Transactional
    public TaskDTO update(TaskDTO taskToUpdate) {
        Task task = taskRepository.findById(taskToUpdate.id).orElseThrow(() -> new TaskNotFoundException(taskToUpdate.toString() + " not found."));
        taskToUpdate.fillTask(task);
        if (taskToUpdate.stageId > 0)
            task.setStage(findStage(taskToUpdate.stageId));

        taskRepository.save(task);
        return TaskDTO.fromTask(task);
    }

    @Transactional
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    private Stage findStage(int id) {
        return stageRepository.findById(id).orElseThrow(() -> new StageNotFoundException("stagre id " + id + "not found."));
    }
}
