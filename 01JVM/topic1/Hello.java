//01 JVM homework 01

//execute javap -v -l Hello and then we get this output:

//Classfile /C:/Users/User/javaHomework/01JVM/Hello.class--------class file directory
//Last modified 2021-6-26; size 494 bytes--------last modify time and size
//		MD5 checksum 2f88bf602313e94bc3891788fe46f35b--------md5 check code
//		Compiled from "Hello.java"--------from where
//public class Hello--------class name
//  minor version: 0--------small version code
//		major version: 52--------big version code and 52.0 mean 1.8
//		flags: ACC_PUBLIC, ACC_SUPER--------public and extends Object
//		Constant pool:--------list constant pool and size is 27
//		#1 = Methodref          #5.#15         // java/lang/Object."<init>":()V--------method ref for Object.init
//		#2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;--------field ref for System.out
//		#3 = Methodref          #18.#19        // java/io/PrintStream.println:(I)V--------method ref for PrintStream.println
//		#4 = Class              #20            // Hello--------Hello class
//		#5 = Class              #21            // java/lang/Object--------Object class
//		#6 = Utf8               <init>
//   #7 = Utf8               ()V
//		   #8 = Utf8               Code
//		   #9 = Utf8               LineNumberTable
//		   #10 = Utf8               main
//		   #11 = Utf8               ([Ljava/lang/String;)V
//		   #12 = Utf8               StackMapTable
//		   #13 = Utf8               SourceFile
//		   #14 = Utf8               Hello.java
//		   #15 = NameAndType        #6:#7          // "<init>":()V--------method description
//		   #16 = Class              #22            // java/lang/System--------System class
//		   #17 = NameAndType        #23:#24        // out:Ljava/io/PrintStream;--------method description
//		   #18 = Class              #25            // java/io/PrintStream--------PrintStream class
//		   #19 = NameAndType        #26:#27        // println:(I)V--------method description
//		   #20 = Utf8               Hello
//		   #21 = Utf8               java/lang/Object
//		   #22 = Utf8               java/lang/System
//		   #23 = Utf8               out
//		   #24 = Utf8               Ljava/io/PrintStream;
//		   #25 = Utf8               java/io/PrintStream
//		   #26 = Utf8               println
//		   #27 = Utf8               (I)V
//		   {
//public Hello();--------Hello class structure method
//		descriptor: ()V--------no args and return void
//		flags: ACC_PUBLIC--------access public
//		Code:
//		stack=1, locals=1, args_size=1--------stack and local variables size is 1,the arg size 1 from this
//		0: aload_0--------load this object
//		1: invokespecial #1                  // Method java/lang/Object."<init>":()V--------invoke init
//		4: return--------finish
//		LineNumberTable:
//		line 4: 0
//
//public static void main(java.lang.String[]);--------main method
//		descriptor: ([Ljava/lang/String;)V--------have a string array param and return void
//		flags: ACC_PUBLIC, ACC_STATIC--------public static
//		Code:
//		stack=3, locals=4, args_size=1
//		0: iconst_1--------add int 1 to stack
//		1: istore_1--------store to local variable 1
//		2: iconst_2--------add int 2 to stack
//		3: istore_2--------store to local variable 2
//		4: iconst_0--------add int 0 to stack
//		5: istore_3--------store to local variable 3
//		6: iload_3--------load m to stack
//		7: bipush        100--------push 100 to stack
//		9: if_icmpge     40--------if m>= 100 then jump to 40
//		12: iload_3--------if m < 100 then load 0 to stack
//		13: bipush        10--------push 10 to stack
//		15: if_icmpne     21--------if m!=10 then jump to 21
//		18: goto          34--------if m==10 then jump to 34
//		21: iload_3--------load m to stack
//		22: bipush        15--------push 15 to stack
//		24: if_icmpne     30--------if m!=15 then jump to 30
//		27: goto          40--------if m==15 then jump to 40
//		30: iload_1--------load local variable 1 to stack
//		31: iload_2--------load local variable 2 to stack
//		32: imul--------multiply operation
//		33: istore_1--------store multiply result to local variable 1
//		34: iinc          3, 1--------increment 1 to local variable 1
//		37: goto          6--------to 6 for cycle
//		40: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;--------get System.out ref
//		43: iload_1--------load local variable 1 to stack
//		44: iload_2--------load local variable 2 to stack
//		45: idiv--------divide operation
//		46: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V--------invoke println method
//		49: return--------default return
//		LineNumberTable:
//		line 7: 0
//		line 8: 2
//		line 9: 4
//		line 10: 12
//		line 11: 18
//		line 13: 21
//		line 14: 27
//		line 16: 30
//		line 9: 34
//		line 18: 40
//		line 19: 49
//		StackMapTable: number_of_entries = 5
//		frame_type = 254 /* append */
//		offset_delta = 6
//		locals = [ int, int, int ]
//		frame_type = 14 /* same */
//		frame_type = 8 /* same */
//		frame_type = 3 /* same */
//		frame_type = 250 /* chop */
//		offset_delta = 5
//		}
//		SourceFile: "Hello.java"



public class Hello {

	public static void main(String[] args) {
		int i = 1;
		short j = 2;
		for (int m = 0; m < 100; m++) {
			if (m == 10) {
				continue;
			}
			if (m == 15) {
				break;
			}
			i = i * j;
		}
		System.out.println(i / j);
	}
}