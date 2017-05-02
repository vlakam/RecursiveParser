tree:
	java Main "$(input)"
	dot -Tpng out.dot -o out.png
	xdg-open out.png

testExample:
	echo "example test"
	java Main "((abc*b|a)*ab(aa|b*)b)*"
	dot -Tpng out.dot -o out.png
	xdg-open out.png

testFail1:
	echo "non-psp zero balance"
	java Main "(a|b))*("

testOk1:
	echo "psp + concat"
	java Main "((a)a)a(a)"
	dot -Tpng out.dot -o out.png
	xdg-open out.png

testFail2:
	echo "psp no choose"
	java Main "((aa)(a|)(b*))"