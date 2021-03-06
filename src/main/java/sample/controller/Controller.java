package sample.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sample.model.Message;
import sample.model.Room;
import sample.model.User;
import sample.service.message.MessagingService;
import sample.service.room.RoomService;
import sample.service.user.RankService;

import java.util.Map;
import java.util.Set;

@EnableWebMvc
@RestController
@RequestMapping("/")
class Controller {
    @Autowired
    private MessagingService messagingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RankService rankService;

    @RequestMapping(value="/salute", method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public @ResponseBody String echo(@RequestParam(name = "name") String name,
                                     @RequestParam(name = "hash") String hash) {
        return rankService.getRank(name, hash).map(rank -> "You are " + rank.getName()).orElse("Unauthorized");
    }

    @RequestMapping(value="/pleaseGeneral", method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public @ResponseBody String getReceivedMessages(@RequestParam(name = "hash") String hash,
                                       @RequestParam(name = "name") String name) {
        return rankService.getRank(name, hash)
                .map(r->constructReceiversResponse(messagingService.getReceivers()).toJSONString())
                .orElse("Unauthorized");
    }

    @RequestMapping(value="/rooms", method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public @ResponseBody String getRooms(@RequestParam(name = "hash") String hash,
                                       @RequestParam(name = "name") String name) {
        return rankService.getRank(name, hash)
                .map(r-> constructRoomsResponse(roomService.getRoomMessagesNumber()).toJSONString())
                .orElse("Unauthorized");
    }

    private JSONObject constructRoomsResponse(Map<Room, Integer> numberByRoom) {
        JSONObject result = new JSONObject();
        numberByRoom.forEach((room, n) -> {
            result.put(room.getName(), n);
        });
        return result;
    }

    private JSONArray constructReceiversResponse(Map<Message, Set<User>> receivers) {
        JSONArray result = new JSONArray();
        receivers.forEach((message, users) -> {
            JSONArray userNames = new JSONArray();
            users.stream().map(User::getName).forEach(userNames::add);

            JSONObject msgInfo = new JSONObject();
            msgInfo.put("id", message.getId());
            msgInfo.put("recipients", userNames);
            result.add(msgInfo);
        });
        return result;
    }
}


