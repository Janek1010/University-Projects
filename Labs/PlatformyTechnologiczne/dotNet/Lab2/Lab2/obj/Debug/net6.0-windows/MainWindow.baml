   M S B A M L   `   `   `  ?????  ;Lab2, Version=1.0.0.0, Culture=neutral, PublicKeyToken=nullclr-namespace:Lab2Lab2  5   	       Lab2.MainWindow   5   	   W SPresentationCore, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=6.0.2.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35^ ZWindowsFormsIntegration, Version=6.0.2.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35H 9http://schemas.microsoft.com/winfx/2006/xaml/presentation      5   	   R NSystem.Xaml, Version=6.0.2.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   $localclr-namespace:Lab2     5   	     ? Title$  
MainWindow??5   	   $	??450??6   $	??800??6)    ? 	Resources 5	         filMenu m? CommandParameter ?? RelativeSource ?? Path  
directMenu??%?  &	        &	 ?     ?? 5
   
   .@?5      m? -   5        $
??View??5       5      ??  ?? *Self+$ Parent?m? 5      -   5        $??Delete??5       5      ??  ?? *Self+$ Parent?5      ?? 5   
   .@?5      m? -   5        $??Create??5       5      ??  ?? *Self+$ Parent?m? 5      -   5        $??Delete??5       5      ??  ?? *Self+$ Parent?5      5      .??5      ?  ? RowDefinitions 5   
   ?? 5      $??1* ?6   ?? 5      $	??12* ?6   ?? 5       $??1* ?6    ? ColumnDefinitions 5"   
   ?? 5#      $??1* ?6   ?? 5$      $??1* ?6   .+?5&   
   c? $??0??6   $??0??6   $
??Left=?6/   .@?5'      o? .@?5(      m? $??Opcje??6   .@?5)      m? -   5        $
??Open??5)      6-   m? 5*      -   5        $
??Exit??5*      6-   5+      5,      5-      R? 5.   
   -   5         R?Name$ MyTree??5.   0   $??0??6   $??1??6#   60   ?? 5/   
   -   5         ??Name$ scrollViewer??5/   4   $??1??6   $??1??6'   $
??Auto=?6J   ?? 50   
   $??2??6   .@?51      ?? -	   5        	 ??Name$
	 RAHS??51      52      53      54      