(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{167:function(e,t,n){e.exports=n.p+"static/media/logo.7f4accb6.svg"},188:function(e,t,n){e.exports=n(412)},193:function(e,t,n){},242:function(e,t,n){},412:function(e,t,n){"use strict";n.r(t);var a={};n.r(a),n.d(a,"open",function(){return z}),n.d(a,"close",function(){return q});var i=n(1),c=n.n(i),l=n(9),o=n.n(l),r=(n(193),n(185)),s=(n(413),n(183)),p=(n(214),n(177)),u=(n(216),n(130)),d=(n(414),n(176)),h=(n(219),n(129)),m=(n(47),n(14)),f=(n(95),n(34)),b=n(55),v=n(56),C=n(59),g=n(57),y=n(60),E=n(35),O=n(167),k=n.n(O),w=n(32),S=n.n(w),j=(n(242),n(137),n(89)),I=(n(246),n(10)),_=(n(93),n(92)),U=(n(138),n(30)),V=n(184),x=(n(166),n(41)),B=x.a.Item,N={clientId:null,name:null,redirectUri:[],scope:[],redirectUriInputValue:"",redirectUriInputVisible:!1,scopeInputValue:"",scopeInputVisible:!1},R=function(e){return 0===Object.keys(e).length},T=function(e){function t(e){var n;return Object(b.a)(this,t),(n=Object(C.a)(this,Object(g.a)(t).call(this,e))).handleOkButtonOnClick=function(){n.props.form.validateFields(function(e,t){if(!e){var a={name:t.name,redirect_uri:n.state.redirectUri,scope:n.state.scope};n.state.clientId?S.a.put("/applications/".concat(n.state.clientId),a).then(function(e){n.props.refreshApplications()}).catch(function(e){}):S.a.post("/applications",a).then(function(e){n.props.refreshApplications()}).catch(function(e){}),n.props.close({}),n.setState(N)}})},n.handleCancelButtonOnClick=function(){n.props.close({}),n.setState(N)},n.handleNameOnChange=function(e){n.setState({name:e.target.value})},n.handleTagConfirm=function(e,t){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:function(){};t&&-1===e.indexOf(t)&&(e=Object(V.a)(e).concat([t])),n(e)},n.handleRedirectUriConfirm=function(){n.handleTagConfirm(n.state.redirectUri,n.state.redirectUriInputValue,function(e){n.setState({redirectUri:e,redirectUriInputValue:"",redirectUriInputVisible:!1})})},n.handleScopeConfirm=function(){n.handleTagConfirm(n.state.scope,n.state.scopeInputValue,function(e){n.setState({scope:e,scopeInputValue:"",scopeInputVisible:!1})})},n.handleRedirectUriRemove=function(e,t){n.handleTagsRemove(e,n.state.redirectUri,function(e){n.setState({redirectUri:e})})},n.handleScopeRemove=function(e,t){n.handleTagsRemove(e,n.state.scope,function(e){n.setState({scope:e})})},n.renderTags=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};return t.handleClose=t.handleClose||function(e,t){},t.color=t.color||"cyan",e.map(function(e,n){var a=e.length>30,i=c.a.createElement(f.a,{color:t.color,key:e,closable:!0,onClose:function(){t.handleClose(n,e)}},a?"".concat(e.slice(0,30),"..."):e);return a?c.a.createElement(U.a,{title:e,key:e},i):i})},n.state=N,n}return Object(y.a)(t,e),Object(v.a)(t,[{key:"componentWillReceiveProps",value:function(e){if(e.visible){var t=R(e.application)?null:e.application.client_id,n=R(e.application)?null:e.application.name,a=R(e.application)?[]:e.application.redirect_uri,i=R(e.application)?[]:e.application.scope;this.setState({clientId:t,name:n,redirectUri:a,scope:i})}}},{key:"handleTagsRemove",value:function(e,t,n){t.splice(e,1),n(t)}},{key:"render",value:function(){var e=this,t=this.props.visible,n=0===Object.keys(this.props.application).length?"Create":"Update",a={labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},i=this.props.form.getFieldDecorator,l=this.state,o=l.redirectUriInputValue,r=l.redirectUriInputVisible,s=l.scopeInputVisible,p=l.scopeInputValue;return c.a.createElement("div",null,c.a.createElement(j.a,{title:n,visible:t,onOk:this.handleOkButtonOnClick,onCancel:this.handleCancelButtonOnClick},c.a.createElement(x.a,{onSubmit:this.handleSubmit},c.a.createElement(B,Object.assign({},a,{label:"Name"}),i("name",{rules:[{required:!0,message:"Please input your app name!"}]})(c.a.createElement(_.a,{required:!0,onChange:this.handleNameOnChange,placeholder:"Please enter an app name"}))),c.a.createElement(B,Object.assign({},a,{label:"Redirect Uri",extra:"Leave blank to use the `urn:ietf:wg:oauth:2.0:oob` the redirect uri."}),this.renderTags(this.state.redirectUri,{handleClose:this.handleRedirectUriRemove}),r&&c.a.createElement(_.a,{type:"text",size:"small",style:{width:78},value:o,onChange:function(t){e.setState({redirectUriInputValue:t.target.value})},onBlur:this.handleRedirectUriConfirm,onPressEnter:this.handleRedirectUriConfirm}),!r&&c.a.createElement(f.a,{onClick:function(){e.setState({redirectUriInputVisible:!0})},style:{background:"#fff",borderStyle:"dashed"}},c.a.createElement(I.a,{type:"plus"})," New")),c.a.createElement(B,Object.assign({},a,{label:"Scope",extra:"Leave blank to use the DEFAULT scopes."}),this.renderTags(this.state.scope,{color:"green",handleClose:this.handleScopeRemove}),s&&c.a.createElement(_.a,{type:"text",size:"small",style:{width:78},value:p,onChange:function(t){e.setState({scopeInputValue:t.target.value})},onBlur:this.handleScopeConfirm,onPressEnter:this.handleScopeConfirm}),!s&&c.a.createElement(f.a,{onClick:function(){e.setState({scopeInputVisible:!0})},style:{background:"#fff",borderStyle:"dashed"}},c.a.createElement(I.a,{type:"plus"})," New")))))}}]),t}(c.a.Component);T.defaultProps={application:{},visible:!1};var A=x.a.create({mapPropsToFields:function(e){return{name:x.a.createFormField({value:e.application.name})}}})(T),D=(n(355),n(131)),P=n(127),F=x.a.Item,W=function(e){function t(){return Object(b.a)(this,t),Object(C.a)(this,Object(g.a)(t).apply(this,arguments))}return Object(y.a)(t,e),Object(v.a)(t,[{key:"_authentication",value:function(e){var t="/oauth/authorize?client_id=".concat(this.props.application.client_id,"&redirect_uri=").concat(e,"&response_type=code");window.open(t,"_blank").focus()}},{key:"render",value:function(){var e=this,t={labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}};return c.a.createElement(j.a,{title:"".concat(this.props.application.name),visible:this.props.visible,onOk:this.props.handleClose,onCancel:this.props.handleClose,footer:[c.a.createElement(m.a,{key:"viewerOK",onClick:this.props.handleClose},"OK")],width:750},c.a.createElement(x.a,null,c.a.createElement(F,Object.assign({},t,{label:"Client ID"}),c.a.createElement("code",null,this.props.application.client_id),c.a.createElement(P.CopyToClipboard,{onCopy:function(){D.a.info("Successfully copied.")},text:this.props.application.client_id},c.a.createElement(m.a,{className:"copy-button",icon:"copy",type:"small"}))),c.a.createElement(F,Object.assign({},t,{label:"Client Secret"}),c.a.createElement("code",null,this.props.application.raw_client_secret),c.a.createElement(P.CopyToClipboard,{onCopy:function(){D.a.info("Successfully copied.")},text:this.props.application.raw_client_secret},c.a.createElement(m.a,{className:"copy-button",icon:"copy",type:"small"}))),c.a.createElement(F,Object.assign({},t,{label:"Redirect Uri"}),(this.props.application.redirect_uri||[]).map(function(t,n){var a=t.length>30,i=c.a.createElement(f.a,{color:"cyan",onClick:function(){e._authentication(t)},key:t,closable:!1},a?"".concat(t.slice(0,30),"..."):t);return c.a.createElement(U.a,{title:"Click to jump to authorization url",key:t},i)})),c.a.createElement(F,Object.assign({},t,{label:"Scopes"}),(this.props.application.scope||[]).map(function(e,t){var n=e.length>30,a=c.a.createElement(f.a,{color:"green",key:e,closable:!1},n?"".concat(e.slice(0,30),"..."):e);return c.a.createElement(U.a,{title:e,key:e},a)}))))}}]),t}(c.a.Component),z=function(){return{type:"open"}},q=function(){return{type:"close"}},K=n(88),L=function(e){function t(e){var n;return Object(b.a)(this,t),(n=Object(C.a)(this,Object(g.a)(t).call(this,e)))._getApplications=function(){S.a.get("/applications",{withCredentials:!0,changeOrigin:!0}).then(function(e){n.setState({applications:e.data})}).catch(function(e){})},n.handleEditButtonOnClick=function(e){n.setState({application:e}),n.props.open()},n.handleCreateButtonOnClick=function(e){n.setState({application:{}}),n.props.open()},n.handleDeleteButtonOnClick=function(e){S.a.delete("/applications/".concat(e.client_id)).then(function(){n._getApplications()}).catch(function(e){})},n.handleShowButtonOnClick=function(e){n.setState({viewerVisible:!0,application:e})},n.columns=[{title:"Client ID",dataIndex:"client_id",key:"client_id"},{title:"Application Name",dataIndex:"name",key:"name",render:function(e){return c.a.createElement("span",null,e)}},{title:"Scopes",key:"scope",dataIndex:"scope",render:function(e){return c.a.createElement("span",null,e.map(function(e){return c.a.createElement(f.a,{color:"blue",key:e},e)}))}},{title:"Action",key:"action",render:function(e,t){return c.a.createElement("span",null,c.a.createElement(m.a,{onClick:n.handleShowButtonOnClick.bind(Object(E.a)(Object(E.a)(n)),t)},"Show"),c.a.createElement(h.a,{type:"vertical"}),c.a.createElement(m.a,{onClick:n.handleEditButtonOnClick.bind(Object(E.a)(Object(E.a)(n)),t)},"Edit"),c.a.createElement(h.a,{type:"vertical"}),c.a.createElement(d.a,{title:"Are you sure delete this application?",onConfirm:n.handleDeleteButtonOnClick.bind(Object(E.a)(Object(E.a)(n)),t),onCancel:function(){},okText:"Yes",cancelText:"No"},c.a.createElement(m.a,{type:"danger"},"Delete")))}}],n.state={applications:[],application:{},viewerVisible:!1},n}return Object(y.a)(t,e),Object(v.a)(t,[{key:"componentWillMount",value:function(){this._getApplications()}},{key:"render",value:function(){var e=this;return c.a.createElement("div",{className:"App"},c.a.createElement(p.a,{className:"header",type:"flex"},c.a.createElement(u.a,{className:"header-logo-banner",span:12},c.a.createElement("img",{className:"header-logo-banner__img",src:k.a,alt:"watchdog log"}),"WatchDog"),c.a.createElement(u.a,{className:"header-menu",span:12},c.a.createElement(m.a,{onClick:this.handleCreateButtonOnClick,type:"primary"},"Create"))),c.a.createElement("div",{className:"main"},c.a.createElement(s.a,{rowKey:"client_id",dataSource:this.state.applications,columns:this.columns})),c.a.createElement(A,{refreshApplications:this._getApplications,visible:this.props.visible,application:this.state.application,close:function(){e.props.close({})}}),c.a.createElement(W,{handleClose:function(){e.setState({viewerVisible:!1})},visible:this.state.viewerVisible,application:this.state.application}))}}]),t}(i.Component),J=Object(K.b)(function(e){return Object(r.a)({},e)},a)(L);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));var M=n(87),Y=Object(M.b)(function(){arguments.length>0&&void 0!==arguments[0]&&arguments[0];switch((arguments.length>1?arguments[1]:void 0).type){case"close":return{visible:!1};case"open":return{visible:!0};default:return{visible:!1}}});S.a.interceptors.request.use(function(e){var t=e.url.startsWith("/")?e.url:"/".concat(e.url),n="".concat("").concat(t);return e.url=n,e}),S.a.interceptors.response.use(function(e){return e}),o.a.render(c.a.createElement(K.a,{store:Y},c.a.createElement(J,null)),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})}},[[188,2,1]]]);
//# sourceMappingURL=main.9c7fccb2.chunk.js.map