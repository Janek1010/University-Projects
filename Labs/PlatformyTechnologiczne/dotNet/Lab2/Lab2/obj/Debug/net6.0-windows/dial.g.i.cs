﻿#pragma checksum "..\..\..\dial.xaml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "3E506ED3F14BAB63B2EDCB3344A97AB67F31EC5A"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using Lab2;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Controls.Ribbon;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Forms.Integration;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace Lab2 {
    
    
    /// <summary>
    /// dial
    /// </summary>
    public partial class dial : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 11 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox TextFieldDialog;
        
        #line default
        #line hidden
        
        
        #line 12 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.RadioButton radioFile;
        
        #line default
        #line hidden
        
        
        #line 13 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.RadioButton radioDirectory;
        
        #line default
        #line hidden
        
        
        #line 14 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.CheckBox CB_ReadOnly;
        
        #line default
        #line hidden
        
        
        #line 15 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.CheckBox CB_Archive;
        
        #line default
        #line hidden
        
        
        #line 16 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.CheckBox CB_Hidden;
        
        #line default
        #line hidden
        
        
        #line 17 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.CheckBox CB_System;
        
        #line default
        #line hidden
        
        
        #line 18 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button OK;
        
        #line default
        #line hidden
        
        
        #line 19 "..\..\..\dial.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button Cancel;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "6.0.2.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/Lab2;component/dial.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\..\dial.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "6.0.2.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.TextFieldDialog = ((System.Windows.Controls.TextBox)(target));
            return;
            case 2:
            this.radioFile = ((System.Windows.Controls.RadioButton)(target));
            return;
            case 3:
            this.radioDirectory = ((System.Windows.Controls.RadioButton)(target));
            return;
            case 4:
            this.CB_ReadOnly = ((System.Windows.Controls.CheckBox)(target));
            return;
            case 5:
            this.CB_Archive = ((System.Windows.Controls.CheckBox)(target));
            return;
            case 6:
            this.CB_Hidden = ((System.Windows.Controls.CheckBox)(target));
            return;
            case 7:
            this.CB_System = ((System.Windows.Controls.CheckBox)(target));
            return;
            case 8:
            this.OK = ((System.Windows.Controls.Button)(target));
            
            #line 18 "..\..\..\dial.xaml"
            this.OK.Click += new System.Windows.RoutedEventHandler(this.OK_Click);
            
            #line default
            #line hidden
            return;
            case 9:
            this.Cancel = ((System.Windows.Controls.Button)(target));
            
            #line 19 "..\..\..\dial.xaml"
            this.Cancel.Click += new System.Windows.RoutedEventHandler(this.CancelDial);
            
            #line default
            #line hidden
            return;
            }
            this._contentLoaded = true;
        }
    }
}

