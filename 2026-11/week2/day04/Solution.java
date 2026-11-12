class Solution {
    public int solution(int coin, int[] cards) {
        init(coin, cards);
        int answer = getMaxTurn();
        return answer;
    }
    
    int C;
    int N;
    int[] cards;
    int startPairCount;
    int startCardCount;
    boolean[] havingCards;
    boolean[] ghostCards;
    private void init(int coin, int[] cards){
        C = coin;
        N = cards.length;
        this.cards = cards;
        havingCards = new boolean[N + 1];
        ghostCards = new boolean[N + 1];
        
        startCardCount = N / 3;
        for(int i = 0; i < startCardCount; i++){   
            int card = cards[i];
            if(havingCards[N + 1 - card]){
                startPairCount++;
            }
            havingCards[card] = true;                        
        }        
    }
    
    private int getMaxTurn() {
        boolean[] ghostCard = new boolean[N + 1];
        int turn = 1;
        int index = startCardCount;
        int pairCount = startPairCount;
        int ghostPairCount = 0;
        int havingCoin = C;
        
        while(index < N){
            int card1 = cards[index++];
            int card2 = cards[index++];
            
            if(havingPairCard(card1, havingCards)){
                if(havingCoin != 0){
                    havingCoin--;
                    pairCount++;
                }            
            }
            else{
                if(havingPairCard(card1, ghostCard)){
                    ghostPairCount++;
                }
                ghostCard[card1] = true;
            }
            
            if(havingPairCard(card2, havingCards)){
                if(havingCoin != 0){
                    havingCoin--;
                    pairCount++;
                }   
            }
            else{
                if(havingPairCard(card2, ghostCard)){
                    ghostPairCount++;
                }
                ghostCard[card2] = true;
            }            
            
            if (pairCount > 0) {
                pairCount--;
            } else if (ghostPairCount > 0 && havingCoin >= 2) {
                ghostPairCount--;
                havingCoin -= 2;
            } else {
                break;
            }

            turn++;
        }
        
        return turn;
    }
        
    private boolean havingPairCard(int card, boolean[] having){
        return having[N + 1 - card];
    }
}