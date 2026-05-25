package com.abin.mallchat.common.sensitive;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.common.algorithm.sensitiveWord.IWordFactory;
    import com.abin.mallchat.common.sensitive.dao.SensitiveWordDao;
    import com.abin.mallchat.common.sensitive.domain.SensitiveWord;
    import org.springframework.stereotype.Component;
    import java.util.List;
    import java.util.stream.Collectors;
    @Component
@RequiredArgsConstructor
public class MyWordFactory implements IWordFactory {    
    private final SensitiveWordDao sensitiveWordDao;
    @Override
    public List<String> getWordList() {
        return sensitiveWordDao.list()
                .stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
    }
}
