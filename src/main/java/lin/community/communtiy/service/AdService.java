package lin.community.communtiy.service;

import lin.community.communtiy.mapper.AdMapper;
import lin.community.communtiy.model.Ad;
import lin.community.communtiy.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdService {
    @Autowired
    private AdMapper adMapper;

    public List<Ad> list(String pos) {
        AdExample navExample = new AdExample();
        navExample.createCriteria()
                .andStatusEqualTo(1)
                .andPosEqualTo(pos)
                .andGmtStartLessThan(System.currentTimeMillis());

        navExample.createCriteria().andGmtEndGreaterThan(System.currentTimeMillis());
        return adMapper.selectByExample(navExample);
    }
}
