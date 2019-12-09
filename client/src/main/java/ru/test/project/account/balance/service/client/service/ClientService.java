package ru.test.project.account.balance.service.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.client.model.RequestProperty;
import ru.test.project.account.balance.service.client.service.factory.TaskFactory;
import ru.test.project.account.balance.service.client.service.thread.AddAmountTask;
import ru.test.project.account.balance.service.client.service.thread.ClearStatisticTask;
import ru.test.project.account.balance.service.client.service.thread.GetAmountTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for client tasks
 */
@Slf4j
@Service
public class ClientService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private StatisticService statisticService;

    /**
     * Path to file with data
     */
    @Value("${file.path}")
    private String filePath;

    /**
     * Get data, create and do tasks
     */
    public void start() {
        RequestProperty requestProperty = getRequestProperty();
        List<TaskFactory> taskFactories = new ArrayList<>();
        for (int i = 0; i < requestProperty.getWCount(); i++) {
            taskFactories.add(() -> new AddAmountTask(accountService, requestProperty.getIds()));
        }
        for (int i = 0; i < requestProperty.getRCount(); i++) {
            taskFactories.add(() -> new GetAmountTask(accountService, requestProperty.getIds()));
        }
        List<Thread> threads = taskFactories.stream()
                .parallel()
                .map(taskFactory -> taskFactory.createTask().getThread())
                .collect(Collectors.toList());

        threads.stream().parallel().forEach(thread -> thread.start());
        threads.forEach(thread -> joinThread(thread));
        if (requestProperty.isClearStatisticAfterRequest()) {
            Thread thread = new ClearStatisticTask(statisticService).getThread();
            thread.start();
            joinThread(thread);
        }
        System.exit(0);
    }

    /**
     * Join given additional thread
     *
     * @param thread - additional thread
     */
    private void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error("General thread error: {}", e.getMessage());
        }
    }

    /**
     * Get request properties
     *
     * @return request properties
     */
    private RequestProperty getRequestProperty() {
        List<String> lines = getDataFromFile();

        int rCount = Integer.parseInt(lines.remove(0));
        int wCount = Integer.parseInt(lines.remove(0));
        boolean clearStatisticAfterRequest = false;
        String last = lines.get(lines.size() - 1);
        if (last.contains(String.valueOf(true)) || last.contains(String.valueOf(false))) {
            clearStatisticAfterRequest = Boolean.valueOf(lines.remove(lines.size() - 1));
        }

        List<Integer> ids = lines.stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        RequestProperty requestProperty = new RequestProperty();
        requestProperty.setRCount(rCount);
        requestProperty.setWCount(wCount);
        requestProperty.setIds(ids);
        requestProperty.setClearStatisticAfterRequest(clearStatisticAfterRequest);
        return requestProperty;
    }

    /**
     * Get data from file
     *
     * @return list of lines from file
     */
    private List<String> getDataFromFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("Error when try read file: {}", e.getMessage());
        }
        ;
        return lines;
    }
}
