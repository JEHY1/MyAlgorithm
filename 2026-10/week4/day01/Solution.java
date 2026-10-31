import java.util.*;

class Solution {
    public int solution(String word, String[] pages) {
        int answer = maxScoreIndex(word, pages);
        return answer;
    }

    private int maxScoreIndex(String word, String[] pages){
        int pageCount = pages.length;
        HtmlInfo[] htmlList = new HtmlInfo[pageCount];

        Map<String, HtmlInfo> urlScoreMapper = new HashMap<>();

        for(int i = 0; i < pageCount; i++){
            htmlList[i] = parse(pages[i], word, i);
            urlScoreMapper.put(htmlList[i].myUrl, htmlList[i]);
        }
        
        for(int i = 0; i < pageCount; i++){
            HtmlInfo html = htmlList[i];
            
            double hrefScore = html.defaultScore / html.hrefList.size();
            
            for(String href : html.hrefList){
                HtmlInfo hrefHtml = urlScoreMapper.get(href);
                if(hrefHtml == null){
                    continue;
                }
                
                hrefHtml.linkScore += hrefScore;
            }
        }
        
        int selIndex = -1;
        double maxScore = -1;

        for(int i = 0; i < pageCount; i++){
            if(maxScore < htmlList[i].defaultScore + htmlList[i].linkScore){
                maxScore = htmlList[i].defaultScore + htmlList[i].linkScore;
                selIndex = i;
            }
        }
        
        return selIndex;
    }

    public HtmlInfo parse(String page, String word, int index){
        String myUrl = null;
        List<String> hrefList = new ArrayList<>();

        boolean makeTag = false;
        boolean makeValue = false;
        boolean startMakeValue = false;
        boolean makeAttribute = false;
        boolean makeText = false;
        boolean nextCharEscape = false;
        boolean isBody = false;

        String tag = null;
        String attribute = null;
        String value = null;

        StringBuilder sb = new StringBuilder();
        StringBuilder text = new StringBuilder();

        for(char c : page.toCharArray()){
            if(c == '<'){
                makeTag = true;
                makeText = false;
                makeValue = false;
                startMakeValue = false;
                makeAttribute = false;
                continue;
            }
            else if(c == '>'){
                if(makeTag && sb.toString().equals("body")){
                    isBody = true;
                }
                
                makeText = true;
                makeTag = false;
                makeAttribute = false;
                makeValue = false;
                startMakeValue = false;
                sb.setLength(0);
                continue;
            }

            if(makeText){
                if(!isBody){
                    continue;
                }
                if(c == '\\'){
                    nextCharEscape = true;
                    continue;
                }

                if(nextCharEscape){
                    nextCharEscape = false;
                    continue;
                }

                int intc = c;
                if((intc >= 65 && intc <= 90) || (intc >= 97 && intc <= 122)){
                    text.append(c);
                }
                else{
                    text.append(' ');
                }
                continue;
            }

            if(makeTag && c == ' '){
                tag = sb.toString();
                makeAttribute = true;
                makeTag = false;
                sb.setLength(0);
                continue;
            }

            if(makeAttribute && (c == '=' || c == ' ')){
                if(sb.isEmpty()){
                    continue;
                }
                attribute = sb.toString();
                makeValue = true;
                makeAttribute = false;
                sb.setLength(0);
                continue;
            }

            if(makeValue && c == '"'){
                startMakeValue = true;
                makeValue = false;
                sb.setLength(0);
                continue;
            }

            if(startMakeValue && c == '"'){
                value = sb.toString();
                makeAttribute = true;
                startMakeValue = false;
                sb.setLength(0);

                if(tag.equals("meta") && attribute.equals("content")){
                    myUrl = value;
                }
                else if (tag.equals("a") && attribute.equals("href")){
                    hrefList.add(value);
                }
                continue;
            }

            if(c == '\\'){
                continue;
            }

            sb.append(c);
        }

        StringTokenizer st = new StringTokenizer(text.toString().toLowerCase(), " ");
        double score = 0;
        word = word.toLowerCase();
        while(st.hasMoreTokens()){
            if(st.nextToken().equals(word)){
                score++;
            }
        }

        return new HtmlInfo(myUrl, hrefList, text.toString(), score, index);
    }

    private static class HtmlInfo{
        int index;
        String myUrl;
        List<String> hrefList;
        String text;
        double defaultScore;
        double linkScore;

        public HtmlInfo(String myUrl, List<String> hrefList, String text, double defaultScore, int index) {
            this.index = index;
            this.myUrl = myUrl;
            this.hrefList = hrefList;
            this.text = text;
            this.defaultScore = defaultScore;
        }
    }
}