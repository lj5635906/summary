import org.springframework.web.client.RestTemplate;

/**
 * @author jie.luo
 * @since 2024/6/25
 */
public class Test {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        Long[] skuIds = {1805261430048690176L,
                1805261443688566784L,
                1805261443785035776L,
                1805261443864727552L,
                1805261443982168064L,
                1805261444070248448L,
                1805261444187688960L,
                1805261514224177152L,
                1805261514299674624L,
                1805261514417115136L,
                1805261514505195520L,
                1805261514584887296L,
                1805261514672967680L,
                1805261514761048064L,
                1805261514844934144L,
                1805261514953986048L,
                1805261515054649344L,
                1805261515163701248L,
                1805261515264364544L,
                1805261705601880064L,
                1805261705681571840L,
                1805261705778040832L,
                1805261705861926912L,
                1805261706000338944L,
                1805261706088419328L,
                1805261706352660480L,
                1805261706444935168L,
                1805261706553987072L,
                1805261706633678848L,
                1805261706713370624L,
                1805261706780479488L,
                1805261706868559872L,
                1805261706981806080L,
                1805261707074080768L,
                1805261707166355456L,
                1805261707267018752L,
                1805261707371876352L,
                1805261707510288384L,
                1805261707711614976L,
                1805263993506631680L,
                1805263993586323456L,
                1805263993737318400L,
                1805263993875730432L,
                1805263993955422208L,
                1805263994043502592L,
                1805263994148360192L,
                1805263994219663360L,
                1805263994341298176L,
                1805266348180508672L,
                1805266348297949184L,
                1805266348365058048L,
                1805266348436361216L,
                1805266348700602368L,
                1805266348805459968L,
                1805266348893540352L,
                1805266348994203648L,
                1805266349082284032L,
                1805266349170364416L,
                1805266349237473280L,
                1805266349308776448L,
                1805266349442994176L,
                1805266349631737856L,
                1805266349719818240L,
                1805266349833064448L
        };

        for (Long skuId : skuIds) {

            String url = "http://192.168.31.103:5000/api/web/search/importGoodsSkuToElasticsearch?skuId=" + skuId;

            String result = restTemplate.getForObject(url, String.class);

            System.out.println(result);
        }

    }

}
