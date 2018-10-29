class Solution {
    public int calculate(String s) {
        return evalPostfix(infix2Postfix(s));
    }
    
    public int rank(char op) {
        switch (op) {
            case '(': return 0;
            case '+': return 1;
            case '-': return 1;
            case '*': return 2;
            case '/': return 2;
        }
        return -1;
    }
    
    public List<Object> infix2Postfix(String s) {
        List<Object> postfix = new LinkedList<Object>();
        Stack<Character> operators = new Stack<Character>();
        
        int numberBuffer = 0;
        boolean numberHold = false;
        
        for (char ch : s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                numberBuffer = numberBuffer * 10 + ch - '0';
                numberHold = true;
            } else {
                if (numberHold) {
                    postfix.add(numberBuffer);
                    numberBuffer = 0;
                    numberHold = false;
                }
                if (ch == ' ' || ch == '\t') continue;
                if (ch == '(')
                    operators.push(ch);
                else if (ch == ')') {
                    while (!operators.isEmpty()) {
                        char ct = operators.pop();
                        if (ct == '(') break;
                        postfix.add(ct);
                    }
                } else {
                    while (!operators.isEmpty() && 
                        rank(operators.peek()) >= rank(ch))
                            postfix.add(operators.pop());
                    operators.push(ch);
                }
            }
        }
        if (numberHold) postfix.add(numberBuffer);
        while (!operators.isEmpty())
            postfix.add(operators.pop());
        
        return postfix;
    }
    
    public int evalPostfix(List<Object> postfix) {
        Stack<Integer> operands = new Stack<Integer>();
        for (Object element : postfix) {
            // System.out.println(element);
            if (element instanceof Character) {
                char op = (Character) element;
                int b = operands.pop();
                int a = operands.pop();
                int ans = 0;
                switch (op) {
                    case '+': ans = a + b; break;
                    case '-': ans = a - b; break;
                    case '*': ans = a * b; break;
                    case '/': ans = a / b; break;
                }
                operands.push(ans);
            } else operands.push((Integer) element);
        }
        return operands.pop();
    }
}

// new O(n) O(1)
/*
1. Overflow
2. 3 2
3. / 0
4. two signs
5. empty string
6. -4+5 5++
*/
class Solution {
    public int calculate(String s) {
        int result = 0, last = 0, buffer = 0;
        char sign = '$';
        s = s + "$";
        for (char ch : s.toCharArray()) {
            if (ch == ' ') continue;
            if (Character.isDigit(ch)) {
                buffer = buffer * 10 + ch - '0';
            } else {
                if (sign == '$') {
                    result = last = buffer;
                } else if (sign == '+') {
                    result += buffer;
                    last = buffer;
                } else if (sign == '-') {
                    result -= buffer;
                    last = -buffer;
                } else if (sign == '*') {
                    result = result - last + last * buffer;
                    last *= buffer;
                } else if (sign == '/') {
                    result = result - last + last / buffer;
                    last /= buffer;
                }
                
                sign = ch; buffer = 0;
            }
        }
        return result;
    }
}