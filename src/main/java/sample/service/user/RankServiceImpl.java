package sample.service.user;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import sample.model.Rank;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class RankServiceImpl implements RankService{
    @Override
    public Optional<Rank> getRank(String userName, String hash) {
        return Arrays.stream(Rank.values())
                .filter(r-> getMD5(userName+r.getKey()).equals(hash))
                .findFirst();
    }

    private String getMD5(String x){
        byte[] thedigest;

        try {
            byte[] bytesOfMessage;
            MessageDigest md;
            bytesOfMessage = x.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
           throw new RuntimeException("MD5 error", e);
        }

        return new String(Base64.encodeBase64(thedigest));
    }
}
