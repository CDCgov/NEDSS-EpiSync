package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.repository.odse.WaTemplateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Dictionary;

@Service @RequiredArgsConstructor
public class MMGPageBuilderRouter implements EpisyncRouter<String, String> {
    private final WaTemplateRepository repository;

    @Override
    public EpisyncRouteResult routeData(EpisyncData<String, String> data) throws EpisyncRouterException {

        WaTemplate template = convert(data);
        WaTemplate saved = repository.save(template);

        String msg = "template_id:" + saved.getWaTemplateUid() + " template_name:" + saved.getTemplateNm();

        return new EpisyncRouteResult(EpisyncRouteResult.RouteResultCode.SUCCESS, msg);
    }

    private WaTemplate convert(EpisyncData<String, String> data) {
        WaTemplate result = new WaTemplate();
        Dictionary<String, String> dict = data.data();

        result.setTemplateType(dict.get("type"));
        result.setXmlPayload("XML Payload");
        result.setFormCd("PG_" + dict.get("name"));
        result.setBusObjType("INV");
        result.setDatamartNm(dict.get("shortName"));
        result.setRecordStatusCd("Active");
        result.setRecordStatusTime(Instant.now());
        result.setLastChgTime(Instant.now());
        result.setLastChgUserId(10000000L);
        result.setDescTxt(dict.get("description"));
        result.setTemplateNm(dict.get("name"));
        result.setPublishIndCd('F');
        result.setAddTime(Instant.now());
        result.setAddUserId(10000000L);
        result.setNndEntityIdentifier(dict.get("profileIdentifier"));

        return result;
    }
}

@RequiredArgsConstructor
@Getter @Setter
class Response {
    public final Long template_id;
    public final String template_name;

    public static Response of(Long id, String name) {
        return new Response(id, name);
    }

}
